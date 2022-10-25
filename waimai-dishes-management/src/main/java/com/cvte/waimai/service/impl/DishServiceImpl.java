package com.cvte.waimai.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.waimai.dao.DishDao;
import com.cvte.waimai.exception.AppException;
import com.cvte.waimai.exception.AppExceptionCodeMsg;
import com.cvte.waimai.service.DishService;
import com.cvte.waimai.utils.DishCacheMqUtils;
import com.cvte.waimai.utils.MsgUtils;
import com.cvte.waimai.utils.RedisUtils;
import org.apache.log4j.Logger;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Dish;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    private DishDao dishDao;

    private static Logger logger = Logger.getLogger(DishServiceImpl.class);

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    RBloomFilter<String> bloomFilter;

    @Autowired
    private DishCacheMqUtils dishCacheMqUtils;

    private static final String KEY_PREFIX = "waimai_dish_%s";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgUtils addDish(Dish dish) {
        this.dishDao.addDish(dish);
        String curDishCacheKey = String.format(KEY_PREFIX, dish.getDishId());
        this.bloomFilter.add(curDishCacheKey);
        logger.info("dish add success");
        return MsgUtils.success(dish);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgUtils deleteDish(int dishId) throws InterruptedException {
        String curDishCacheKey = String.format(KEY_PREFIX, dishId);
        redisUtils.remove(curDishCacheKey);
        this.dishDao.deleteDish(dishId);
        dishCacheMqUtils.sendMessage(curDishCacheKey);
        logger.info(dishId + ": dish delete success");
        return MsgUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgUtils updateDish(Dish dish) throws InterruptedException {
        Dish oldDish = this.dishDao.getDishById(dish.getDishId());
        if (oldDish == null) {
            throw new AppException(AppExceptionCodeMsg.ILLEGAL_DISH);
        }
        if (null == dish.getDishName()) {
            dish.setDishName(oldDish.getDishName());
        }
        if (null == dish.getImageUrl()) {
            dish.setImageUrl(oldDish.getImageUrl());
        }
        if (0L == dish.getPrice()) {
            dish.setPrice(oldDish.getPrice());
        }
        if (0 == dish.getMerchantId()) {
            dish.setMerchantId(oldDish.getMerchantId());
        }
        String curDishCacheKey = String.format(KEY_PREFIX, dish.getDishId());
        redisUtils.remove(curDishCacheKey);
        this.dishDao.updateDish(dish);
        dishCacheMqUtils.sendMessage(curDishCacheKey);
        logger.info(dish.getDishId() + ": dish update success");
        return MsgUtils.success();
    }

    @Override
    public MsgUtils getDishById(int dishId) {
        String curDishCacheKey = String.format(KEY_PREFIX, dishId);
        boolean isExist = this.bloomFilter.contains(curDishCacheKey);
        if (!isExist) {
            throw new AppException(AppExceptionCodeMsg.DISH_NOT_FOUND);
        }
        String cache = redisUtils.get(curDishCacheKey);
        if (null != cache) {
            //if cache exist return
            logger.info("get dish cache: " + curDishCacheKey);
            Dish dish = JSONObject.parseObject(cache, Dish.class);
            redisUtils.delayExpireTime(curDishCacheKey, 60L * 5);
            return MsgUtils.success(dish);
        } else {
            //if cache no exist find in db and push in cache
            Dish dish;
            synchronized (DishServiceImpl.class) {
                //query cache again
                String cache1 = redisUtils.get(curDishCacheKey);
                if (cache1 != null) {
                    dish = JSONObject.parseObject(cache1, Dish.class);
                    return MsgUtils.success(dish);
                }
                dish = this.dishDao.getDishById(dishId);
                if (dish != null) {
                    String dishStr = JSONObject.toJSONString(dish);
                    Random random = new Random();
                    int randomNum = random.nextInt(300);
                    redisUtils.set(curDishCacheKey, dishStr, 60L * 5 + randomNum);
                } else {
                    throw new AppException(AppExceptionCodeMsg.DISH_NOT_FOUND);
                }
            }
            return MsgUtils.success(dish);
        }
    }

    @Override
    public MsgUtils getDishes(int curPage, int pageSize) {
        List<Dish> dishes = this.dishDao.getDishes((curPage-1) * pageSize, pageSize);
        if (dishes == null || dishes.size() == 0) {
            throw new AppException(AppExceptionCodeMsg.DISH_NOT_FOUND);
        }
        return MsgUtils.success(dishes);
    }
}
