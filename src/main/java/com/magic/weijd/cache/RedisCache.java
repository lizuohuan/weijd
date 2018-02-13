package com.magic.weijd.cache;

import com.magic.weijd.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author lzh
 * @create 2017/9/11 9:49
 */
public class RedisCache {

    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    /**
     * 缓存value操作
     * @param key
     * @param v
     * @param time
     * @return
     */
    public static boolean put(String key, Object v, long time) {
        RedisTemplate redisTemplate = null;
        try {
            redisTemplate = getRedisTemplate();
            if (null == redisTemplate) {
                return false;
            }
            ValueOperations<String, Object> valueOps =  redisTemplate.opsForValue();
            valueOps.set(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存value操作
     * @param k
     * @param v
     * @return
     */
    public static boolean put(String k, String v) {
        return put(k, v, -1);
    }

    /**
     * 缓存value操作
     * @param k
     * @param v
     * @return
     */
    public static boolean put(String k, Object v) {
        return put(k, v, -1);
    }

    /**
     * 获取缓存值.
     * @param key
     * @return
     */
    public static Object get(String key) {
        try {
            ValueOperations<String, Object> valueOps =  getRedisTemplate().opsForValue();
            Object val = valueOps.get(key);
            return val;
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取缓存值.
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        try {
            ValueOperations<String, Object> valueOps =  getRedisTemplate().opsForValue();
            Object val = valueOps.get(key);
            return (T) val;
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取缓存
     * @param k
     * @return
     */
    protected static String getValue(String k) {
        RedisTemplate redisTemplate = null;
        try {
            redisTemplate = getRedisTemplate();
            if (null == redisTemplate) {
                return null;
            }
            ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
            Object val = valueOps.get(k);
            return val == null ? null : val.toString();
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     * @param key
     * @return
     */
    protected static boolean contains(String key) {
        try {
            return getRedisTemplate().hasKey(key);
        } catch (Throwable t) {
            logger.error("判断缓存存在失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    /**
     * 移除缓存
     * @param key
     * @return
     */
    public static boolean remove(String key) {
        try {
            getRedisTemplate().delete(key);
            return true;
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    public static RedisTemplate getRedisTemplate() {
        return (RedisTemplate) SpringUtil.getBean("redisTemplate");
    }

}
