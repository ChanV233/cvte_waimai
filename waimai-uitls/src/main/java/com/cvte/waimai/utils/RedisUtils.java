package com.cvte.waimai.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private RedisUtils redisUtils;

    /**
     * 写入缓存
     *
     * @param key   redis键
     * @param value redis值
     * @return 是否成功
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key   redis键
     * @param value redis值
     * @return 是否成功
     */
    public boolean set(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给key延时
     * @param key
     * @param expireTime
     * @return
     */
    public boolean delayExpireTime(final String key, Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的键值对
     *
     * @param keys Redis键名数组
     */
    public void removeByKeys(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除Redis key
     *
     * @param pattern 键名包含字符串（如：myKey*）
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除key,也删除对应的value
     *
     * @param key Redis键名
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key Redis键名
     * @return 是否存在
     */
    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key Redis键名
     * @return 是否存在
     */
    public String get(final String key) {
        String result = null;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key     Redis键
     * @param hashKey 哈希键
     * @param value   哈希值
     */
    public void hmSet(String key, String hashKey, String value) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key     Redis键
     * @param hashKey 哈希键
     * @return 哈希值
     */
    public String hmGet(String key, String hashKey) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 判断hash是否存在键
     *
     * @param key     Redis键
     * @param hashKey 哈希键
     * @return 是否存在
     */
    public boolean hmHasKey(String key, String hashKey) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.hasKey(key, hashKey);
    }

    /**
     * 删除hash中一条或多条数据
     *
     * @param key      Redis键
     * @param hashKeys 哈希键名数组
     * @return 删除数量
     */
    public long hmRemove(String key, String... hashKeys) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.delete(key, hashKeys);
    }

    /**
     * 获取所有哈希键值对
     *
     * @param key Redis键名
     * @return 哈希Map
     */
    public Map<String, String> hashMapGet(String key) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    /**
     * 保存Map到哈希
     *
     * @param key Redis键名
     * @param map 哈希Map
     */
    public void hashMapSet(String key, Map<String, String> map) {
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
    }

    /**
     * 列表-追加值
     *
     * @param key   Redis键名
     * @param value 列表值
     */
    public void lPush(String key, String value) {
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.rightPush(key, value);
    }

    /**
     * 列表-获取指定范围数据
     *
     * @param key   Redis键名
     * @param start 开始行号
     * @param end   结束行号
     * @return 列表
     */
    public List<String> lRange(String key, long start, long end) {
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.range(key, start, end);
    }

    /**
     * 集合添加
     *
     * @param key   Redis键名
     * @param value 值
     */
    public void add(String key, String value) {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key Redis键名
     * @return 集合
     */
    public Set<String> setMembers(String key) {
        SetOperations<String, String> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key   Redis键名
     * @param value 值
     * @param score 排序号
     */
    public void zAdd(String key, String value, double score) {
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, score);
    }

    /**
     * 有序集合-获取指定范围
     *
     * @param key        Redis键
     * @param startScore 开始序号
     * @param endScore   结束序号
     * @return 集合
     */
    public Set<String> rangeByScore(String key, double startScore, double endScore) {
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, startScore, endScore);
    }

    /**
     * 模糊查询Redis键名
     *
     * @param pattern 键名包含字符串（如：myKey*）
     * @return 集合
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @PostConstruct
    public void init() {
        redisUtils = this;
        redisUtils.redisTemplate = this.redisTemplate;
    }
}