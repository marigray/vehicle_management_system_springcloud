package com.dra.gps.jms;

import com.alibaba.fastjson.JSON;

import com.dra.gps.redis.RedisUtil;
import com.dra.gps.service.CarService;
import com.dra.gps.service.GPSLogService;
import com.dra.gps.service.GPSService;

import com.dra.pojo.gps.GpsLog;
import com.dra.pojo.gps.LocationOriginal;
import com.dra.utils.FinalValueSet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class GPSConsumer {
    @Resource
    private CarService carService;

    @Resource
    private GPSService gpsService;

    @Resource
    private GPSLogService gpsLogService;

    @Resource
    private RedisUtil redisUtil;


    @JmsListener(destination = "location")
    public void receiveQueue(MqttMessage message) {
        log.info("设备请求");
        LocationOriginal locationOriginal = JSON.parseObject(message.toString(), LocationOriginal.class);
        //参数转换
        GpsLog gpsLog = new GpsLog();
        gpsLog.setGpsId(locationOriginal.getGpsId());
        gpsLog.setTime(new Date());
        log.debug("当前时间:"+gpsLog.getTime());
        gpsLog.setPositionX(Double.parseDouble(locationOriginal.getLongitude()));
        gpsLog.setPositionY(Double.parseDouble(locationOriginal.getLatitude()));
        //放入redis数据库
        String carId = gpsService.searchCarByGpsId(gpsLog.getGpsId()).getCarId();
        log.debug(carId);
        gpsLog.setCarId(carId);

        redisUtil.setList(FinalValueSet.GPS,gpsLog.getGpsId(),gpsLog);
        //放入mysql数据库
        gpsLogService.add(gpsLog);
    }
    @JmsListener(destination="test01")
    public void TestGps(MqttMessage message){
        log.info("测试设备进入");
        log.info("测试数据:"+message.toString());
    }
}
