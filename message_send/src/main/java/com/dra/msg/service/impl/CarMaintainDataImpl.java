package com.dra.msg.service.impl;


import com.dra.msg.config.Mail;
import com.dra.msg.mapper.CarMapper;
import com.dra.msg.mapper.PushMailMapper;
import com.dra.msg.redis.RedisUtil;
import com.dra.msg.service.CarMaintainData;
import com.dra.pojo.msg.Car;
import com.dra.pojo.msg.PushMail;
import com.dra.utils.FinalValueSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CarMaintainDataImpl implements CarMaintainData {

    @Resource
    private CarMapper carMapper;

    @Resource
    private PushMailMapper pushMailMapper;

    @Resource
    private Mail mail;

    @Resource
    private RedisUtil redisUtil;
    private final Long TIME = 1000 * 60 * 60 * 24 * 150L;

    private boolean isTime(Date date1) {
        long i = new Date().getTime() - date1.getTime();
        System.out.println("i="+i);
        return i<= TIME;
    }


    @Override
    public Object choose() {
        log.info("已选择");
        Set<Car> carMaintain = new HashSet<>();//提醒推送列表
        //获取车辆信息
        log.info("获取车辆信息");
        List<Car> result = carMapper.selectAll().getResult();
        System.out.println(result);
        //判断是否需要保养
        log.info("判断是否需要保养");
        for (Car car : result) {

            if (car.getLastMaintain() == null) {
                if (isTime(car.getCarProTime()))
                    System.out.println("需要保养");
                    carMaintain.add(car);
            } else {
                if (isTime(car.getLastMaintain()))
                    System.out.println("需要保养");
                    carMaintain.add(car);
            }

        }
        //注入Redis

        if (carMaintain.size() != 0)
            log.info("注入Redis");
            redisUtil.setString(FinalValueSet.MAINTAIN, carMaintain, 0, 1);
        return FinalValueSet.OK_STAT;
    }

    @Override
    public Object send() {
        log.info("已发送");
        //从redis获取需要推送的信息
        log.info("从redis获取需要推送的信息");
        Object string = redisUtil.getString(FinalValueSet.MAINTAIN, 1);
        System.out.println("信息:"+string);
        if (string==null)
            return null;
        Set<Car> carSet = (HashSet<Car>) string;
        if (carSet.size() == 0)
            return null;
        System.out.println(carSet);
        //获取需要提醒的邮箱
        log.info("获取需要提醒的邮箱");
        List<PushMail> result = pushMailMapper.selectAll().getResult();
        System.out.println(result);
        //整理数据
        log.info("整理数据");
        StringBuilder mailData = new StringBuilder("<p>" + "车辆id" + "|" + "上线时间" + "|" + "上一次保养时间" + "</p>\n");
        for (Car car : carSet) {
            mailData.append("<p>").append(car.getCarId()).append("|").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getCarProTime())).append("|").append(car.getLastMaintain()).append("</p>\n");
        }
        String[] mails = new String[result.size()];
        for (int i = 0; i < mails.length; i++) {
            mails[i] = result.get(i).getMail();
        }
        //发送邮件信息
        log.info("发送邮件信息");
        System.out.println(mailData);
//        System.out.println(mailData);
        try {
            mail.send1(String.valueOf(mailData), "请注意以下车辆近期需要保养", mails);
        }catch (Exception e)
        {
            System.out.println("错误");
            e.printStackTrace();
        }

        //清除队列
        log.info("清除队列");
        redisUtil.delString(FinalValueSet.MAINTAIN, 1);


        return FinalValueSet.OK_STAT;
    }
}
