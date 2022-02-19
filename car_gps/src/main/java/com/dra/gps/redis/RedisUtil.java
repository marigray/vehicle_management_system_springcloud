package com.dra.gps.redis;


import com.dra.pojo.gps.CarState;
import com.dra.pojo.gps.GpsLog;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RedisUtil {
    @Resource
    private JedisPool jedisPool;
    private Jedis jedis;
    private final String PASSWORD = "2545570457@qq";
    public void getJedis(){
        this.jedis = jedisPool.getResource();
    }

    public void hasJedis(){
        if (this.jedis==null){
            getJedis();
        }
//        this.jedis.auth(this.PASSWORD);
    }

    public Long setList(String key, String id, Object object) {
        hasJedis();

        //序列化
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] var2 = SerializeUtil.serialize(id);
        byte[] var3 = SerializeUtil.serialize(object);
        return this.jedis.hset(var1,var2,var3);
    }

    public ArrayList<GpsLog> getGpsLogListAll(String key) {
        hasJedis();
        //关键字序列化
        byte[] var1 = SerializeUtil.serialize(key);
        List<byte[]> var2 = this.jedis.hvals(var1);
        ArrayList<GpsLog> logs = new ArrayList<>();
        //开始反序列化
        for (byte[] v : var2) {
            logs.add((GpsLog) SerializeUtil.deserialize(v));
        }
        return logs;
    }
    public GpsLog getGpsLog(String key,String id){
        hasJedis();
        //关键字序列化
        byte[] var1 = SerializeUtil.serialize(key);
        byte[] var2 = SerializeUtil.serialize(id);
        byte[] var3 = this.jedis.hget(var1, var2);
//        System.out.println("["+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) +"]"+SerializeUtil.deserialize(var3));
        //开始反序列化
        return (GpsLog)SerializeUtil.deserialize(var3);
    }

    public ArrayList<CarState> getCarState(String key){
        hasJedis();
        byte[] var1 = SerializeUtil.serialize(key);
        List<byte[]> var2 = this.jedis.hvals(var1);
        ArrayList<CarState> var3 = new ArrayList<>();
        for (byte[] bytes : var2) {
            var3.add((CarState) SerializeUtil.deserialize(bytes));
        }

        return var3;
    }
}
