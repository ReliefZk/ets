package com.reliefzk.middleware.ets.schedule.redis.impl;

import java.util.Set;

import com.reliefzk.middleware.ets.schedule.common.ScheduleConstants;
import com.reliefzk.middleware.ets.schedule.redis.RedisRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

/**
 * redis data access implement
 */
@Repository
public class RedisRepositoryImpl implements RedisRepository {

    /**
     * jedis pool 对象
     */
    private JedisPool jedisPool;

    @Override
    public boolean expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, seconds);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean lpush(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key, val);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean lrem(String key, int count, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lrem(key, count, val);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean publish(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.publish(channel, message);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean subscribe(String channel, JedisPubSub listener) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(listener, channel);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean set(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, val);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean set(String key, String val, int expireSeconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, expireSeconds, val);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean setWithTx(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction tx = jedis.multi();//开始事务
            tx.set(key, val);
            tx.exec();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean delWithTx(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction tx = jedis.multi();//开始事务
            tx.del(key);
            tx.exec();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean sadd(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.sadd(key, val);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean srem(String key, String... vals) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.srem(key, vals);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public boolean deleteKey(String...keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long state = jedis.del(keys);
            if(state > 0){
                return true;
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    @Override
    public boolean hset(String redisKey, String field, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(redisKey, field, val);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return true;
    }

    @Override
    public String hget(String redisHashName, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(redisHashName, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public boolean competeLock(String key) {
        return doCompeteLock(key);
    }

    @Override
    public void expireCompeteLock(String locKey, int exSeconds) {
        set(locKey, ScheduleConstants.LOCK_VAL, exSeconds);
    }

    @Override
    public void setIfAbsent(String key, String value, int expireSeconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.get(key);
            if(StringUtils.isNotBlank(result)){
                return;
            }
            jedis.setex(key, expireSeconds, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Set<String> keys(String keyPattern) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(keyPattern);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public long hashCount(String hashKey) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hlen(hashKey);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Set<Tuple> zrangByScoreWithScore(String key, long startScore, long endScore) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScoreWithScores(key, startScore, endScore);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public long zremByScore(String key, long startScore, long endScore) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByScore(key, startScore, endScore);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private boolean doCompeteLock(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //set not exists
            long isSetOk = jedis.setnx(key, ScheduleConstants.LOCK_VAL);
            if(isSetOk > 0L){
                expireCompeteLock(key, ScheduleConstants.LOCK_VAL_EXPIRE_SECONDS);
                return true;
            }
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
