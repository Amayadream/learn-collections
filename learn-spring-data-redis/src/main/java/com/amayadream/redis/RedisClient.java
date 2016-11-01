package com.amayadream.redis;

import com.alibaba.fastjson.JSON;
import com.amayadream.redis.model.SimpleObject;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * @author :  Amayadream
 * @date :  2016.07.26 21:31
 */
public class RedisClient {

    public static JedisPool jedisPool;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("properties/config");
        int maxActive = Integer.parseInt(bundle.getString("redis.pool.maxActive"));
        int maxIdle = Integer.parseInt(bundle.getString("redis.pool.maxIdle"));
        int maxWait = Integer.parseInt(bundle.getString("redis.pool.maxWait"));

        String ip = bundle.getString("redis.ip");
        int port = Integer.parseInt(bundle.getString("redis.port"));

        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(maxActive);
        //设置最大空闲数
        config.setMaxIdle(maxIdle);
        //设置超时时间
        config.setMaxWaitMillis(maxWait);

        //初始化连接池
        jedisPool = new JedisPool(config, ip, port);
    }

    /**
     * 向缓存中设置字符串内容
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }
    }

    /**
     * 向缓存里设置对象
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.set(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }
    }

    /**
     * 删除缓存中得对象，根据key
     * @param key
     * @return
     */
    public static boolean del(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }
    }

    /**
     * 根据key 获取内容
     * @param key
     * @return
     */
    public static Object get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }
    }


    /**
     * 根据key 获取对象
     * @param key
     * @return
     */
    public static <T> T get(String key,Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }
    }

    @Test
    public void test(){
        String s = "hello, world";
//        set("Amayadream", s);
        SimpleObject object = new SimpleObject("Amayadream", "hello, Amayadream");
//        set("message", object);
        System.out.println(String.valueOf(get("message")));
        object = null;
        object = get("message", SimpleObject.class);
        System.out.println(JSON.toJSONString(object));
    }

}
