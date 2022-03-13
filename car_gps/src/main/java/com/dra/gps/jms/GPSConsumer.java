package com.dra.gps.jms;

import com.dra.gps.redis.RedisUtil;
import com.dra.gps.service.CarService;
import com.dra.gps.service.GPSLogService;
import com.dra.gps.service.GPSService;
import com.dra.pojo.gps.GpsLog;
import com.dra.utils.FinalValueSet;
import com.dra.utils.GPSMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


//    @JmsListener(destination = "location")
//    public void receiveQueue(MqttMessage message) {
//        log.info("设备请求");
//        LocationOriginal locationOriginal = JSON.parseObject(message.toString(), LocationOriginal.class);
//        //参数转换
//        GpsLog gpsLog = new GpsLog();
//        gpsLog.setGpsId(locationOriginal.getGpsId());
//        gpsLog.setTime(new Date());
//        log.debug("当前时间:"+gpsLog.getTime());
//        gpsLog.setPositionX(Double.parseDouble(locationOriginal.getLongitude()));
//        gpsLog.setPositionY(Double.parseDouble(locationOriginal.getLatitude()));
//        //放入redis数据库
//        String carId = gpsService.searchCarByGpsId(gpsLog.getGpsId()).getCarId();
//        log.debug(carId);
//        gpsLog.setCarId(carId);
//
//        redisUtil.setList(FinalValueSet.GPS,gpsLog.getGpsId(),gpsLog);
//        //放入mysql数据库
//        gpsLogService.add(gpsLog);
//    }
    @JmsListener(destination="GPS-LOCATION-MESSAGE")
    public void TestGps(MqttMessage message){
        String gpsMsg = message.toString();
        log.info("测试设备进入");
        log.info("测试数据:"+gpsMsg);
        // 获取GPSID
        Pattern p = Pattern.compile("\\#[\\s\\S]+\\#");
        Matcher matcher = p.matcher(gpsMsg);
        String id_replace = null;
        if (matcher.find()){
            String id_group = matcher.group(0);
            //消除GPS信息中的ID数据
            gpsMsg = gpsMsg.replace(id_group, "");
            id_replace = id_group.replace("#", "");
            log.info("GPSID :"+id_replace);
        }else {
            log.warn("信息无ID");
        }
        //根据GPSID查询车辆ID
        String carId = gpsService.searchCarByGpsId(id_replace).getCarId();
        //建立车辆位置信息对象
        GpsLog gpsLog = null;
        try {
            gpsLog = GPSMessage.analysisMessage(gpsMsg).analysisPositionMessage(carId, id_replace);;
        }catch (Exception e){
            log.error("数据为空");
            return;
        }

        log.info(String.valueOf(gpsLog));
        //上传Redis数据库
        redisUtil.setList(FinalValueSet.GPS,gpsLog.getGpsId(),gpsLog);

        //上传MySql数据库
        try {
            gpsLogService.add(gpsLog);
        }catch (DuplicateKeyException e){
            log.error("车辆数据时间冲突");
        }

    }
}
