package com.dra.login.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
public class RedisUtil {
    @Resource
    private JedisPool jedisPool;
    private Jedis jedis;
    private final String PASSWORD = "123456";
    public void getJedis(){
        this.jedis = jedisPool.getResource();
    }

    public void hasJedis(){
        if (this.jedis==null){
            getJedis();
        }
        this.jedis.auth(this.PASSWORD);
    }

    public Long setSET(String key, String id, Object object) {
        hasJedis();

        //序列化
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] var2 = SerializeUtil.serialize(id);
        byte[] var3 = SerializeUtil.serialize(object);
        return this.jedis.hset(var1,var2,var3);
    }

    /**
     * @param time 秒
     */
    public String setString(String key,String value,int time){
        if (this.jedis==null){
            this.jedis = jedisPool.getResource();
        }
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] var2 = SerializeUtil.serialize(value);
        return this.jedis.setex(var1,time,var2);
    }

    public String getString(String key,int sel){

        if (this.jedis==null){
            this.jedis = jedisPool.getResource();
            this.jedis.select(sel);
        }
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] bytes = this.jedis.get(var1);
        return (String) SerializeUtil.deserialize(bytes);
    }
}
