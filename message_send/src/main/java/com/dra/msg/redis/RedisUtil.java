package com.dra.msg.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
public class RedisUtil {
    @Resource
    private JedisPool jedisPool;
    private Jedis jedis;
    private final String PASSWORD = "2545570457@qq";

    public void getJedis() {
        this.jedis = jedisPool.getResource();
    }

    public void hasJedis() {
        if (this.jedis == null) {
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
        Long hset = this.jedis.hset(var1, var2, var3);
        closeJedis();
        return hset;
    }

    public void sel(int sel){
        if (this.jedis == null) {
            this.jedis = jedisPool.getResource();
            this.jedis.select(sel);
        }
    }
    /**
     * @param time 秒
     */
    public String setString(String key, Object value, int time, int sel) {
        sel(sel);
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] var2 = SerializeUtil.serialize(value);
        if (time == 0)
            return this.jedis.set(var1, var2);
        String setex =null;
        try {
            setex = this.jedis.setex(var1, time, var2);
        }catch (Exception e){
            closeJedis();
            this.jedis = jedisPool.getResource();
        }


        return setex;

    }

    public Object getString(String key,int sel){

        sel(sel);
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] bytes = this.jedis.get(var1);
        closeJedis();
        if (bytes.length==0)
            return null;
        return SerializeUtil.deserialize(bytes);
    }

    public Object delString(String key,int sel){
        sel(sel);
        byte[] var1 = SerializeUtil.serialize(key);
        Long del = this.jedis.del(var1);
        closeJedis();
        return del;
    }

    //释放连接
    public void closeJedis(){
        this.jedis.close();
    }
}
