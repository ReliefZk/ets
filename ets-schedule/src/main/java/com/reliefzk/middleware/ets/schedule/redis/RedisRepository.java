package com.reliefzk.middleware.ets.schedule.redis;

import java.util.Set;

import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

/**
 * redis data interface
 */
public interface RedisRepository {

    /**
     * 设置key失效时间
     * @param key
     * @param seconds
     * @return
     */
    boolean expire(String key, int seconds);

    /**
     * 将值val插入到列表key的表头
     * @param key
     * @param val
     * @return
     */
    boolean lpush(String key, String val);

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素
     * @param key
     * @param count
     * @param val
     * @return
     */
    boolean lrem(String key, int count, String val);

    /**
     * pub/sub中publish消息
     * @param channel
     * @param message
     * @return
     */
    boolean publish(String channel, String message);

    /**
     * pub/sub中sub消息
     * @param channel
     * @param listener
     * @return
     */
    boolean subscribe(String channel, JedisPubSub listener);

    /**
     * get
     * @param key
     * @return
     */
    String get(String key);

    /**
     * set
     * @param key
     * @param val
     * @return
     */
    boolean set(String key, String val);

    /**
     *
     * @param key
     * @param val
     * @param expireSeconds 失效时间
     * @return
     */
    boolean set(String key, String val, int expireSeconds);

    /**
     * 事务性操作key 保证原子性
     * @param key
     * @param val
     * @return
     */
    boolean setWithTx(String key, String val);

    /**
     * 原子操作删除key
     * @param key
     * @return
     */
    boolean delWithTx(String key);

    /**
     * add val to set
     * @param key
     * @param val
     * @return
     */
    boolean sadd(String key, String val);
    /**
     * 返回set中所有成员
     * @param key
     * @return
     */
    Set<String> smembers(String key);

    /**
     * 移除set中一个或多个元素
     * @param key
     * @param vals
     * @return
     */
    boolean srem(String key, String... vals);

    /**
     * delete redis key
     * @param keys
     * @return
     */
    boolean deleteKey(String... keys);

    /**
     * hash set k:v
     * @param redisKey
     * @param field
     * @param val
     * @return
     */
    boolean hset(String redisKey, String field, String val);

    String hget(String redisHashName, String field);

    /**
     * 竞争全局锁
     * @param key
     * @return
     */
    boolean competeLock(String key);

    /**
     * 释放锁
     * @param locKey
     * @param exSeconds
     */
    void expireCompeteLock(String locKey, int exSeconds);

    /**
     * 不存在设置
     * @param key
     * @param expireSeconds
     */
    void setIfAbsent(String key, String val, int expireSeconds);

    /**
     * key
     * @param keyPattern
     * @return
     */
    Set<String> keys(String keyPattern);

    /**
     * hash count
     * @param hashKey
     * @return
     */
    long hashCount(String hashKey);

    /**
     *
     * @param key
     * @param startScore
     * @param endScore
     */
    Set<Tuple> zrangByScoreWithScore(String key, long startScore, long endScore);

    /**
     * @param key
     * @param startScore
     * @param endScore
     * @return
     */
    long zremByScore(String key, long startScore, long endScore);
}
