package com.cvte.waimai.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.waimai.dao.DishDao;
import com.cvte.waimai.exception.AppException;
import com.cvte.waimai.exception.AppExceptionCodeMsg;
import com.cvte.waimai.service.DishService;
import com.cvte.waimai.utils.MsgUtils;
import com.cvte.waimai.utils.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Dish;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    private DishDao dishDao;

    private static Logger logger = Logger.getLogger(DishServiceImpl.class);

    @Resource
    private RedisUtils redisUtils;

    private static final String KEY_PREFIX = "waimai_dish_%s";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgUtils addDish(Dish dish) {
        this.dishDao.addDish(dish);
        logger.info("dish add success");
        return MsgUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgUtils deleteDish(int dishId) throws InterruptedException {
        String curDishCacheKey = String.format(KEY_PREFIX, dishId);
        redisUtils.remove(curDishCacheKey);
        this.dishDao.deleteDish(dishId);
        Thread.sleep(100);
        redisUtils.remove(curDishCacheKey);
        logger.info(dishId + ": dish delete success");
        return MsgUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgUtils updateDish(Dish dish) throws InterruptedException {
        Dish oldDish = this.dishDao.getDishById(dish.getDish_id());
        if (null == dish.getDish_name()) {
            dish.setDish_name(oldDish.getDish_name());
        }
        if (null == dish.getImage_url()) {
            dish.setImage_url(oldDish.getImage_url());
        }
        if (0L == dish.getPrice()) {
            dish.setPrice(oldDish.getPrice());
        }
        if (0 == dish.getMerchant_id()) {
            dish.setMerchant_id(oldDish.getMerchant_id());
        }
        String curDishCacheKey = String.format(KEY_PREFIX, dish.getDish_id());
        redisUtils.remove(curDishCacheKey);
        this.dishDao.updateDish(dish);
        Thread.sleep(100);
        redisUtils.remove(curDishCacheKey);logger.info(dish.getDish_id() + ": dish update success");
        return MsgUtils.success();
    }

    @Override
    public MsgUtils getDishById(int dishId) {
        String curDishCacheKey = String.format(KEY_PREFIX, dishId);
        String cache = redisUtils.get(curDishCacheKey);
        if (null != cache) {
            //if cache exist return
            logger.info("get dish cache: " + curDishCacheKey);
            Dish dish = JSONObject.parseObject(cache, Dish.class);
            redisUtils.delayExpireTime(curDishCacheKey, 60L * 5);
            return MsgUtils.success(dish);
        } else {
            //if cache no exist find in db and push in cache
            Dish dish = this.dishDao.getDishById(dishId);
            if (dish != null) {
                String dishStr = JSONObject.toJSONString(dish);
                redisUtils.set(curDishCacheKey, dishStr, 60L * 5);
            } else {
                throw new AppException(AppExceptionCodeMsg.DISH_NOT_FOUND);
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
