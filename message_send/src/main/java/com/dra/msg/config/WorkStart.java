package com.dra.msg.config;


import com.dra.msg.service.CarMaintainData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WorkStart {
    //执行周期
    private long time = 1;

    /**
     *  筛选1小时执行一次
     *  邮件发送1天执行一次
     */
    @Resource
    private CarMaintainData carMaintainData;
    public void run() {
       ScheduledExecutorService executorService1 = Executors.newScheduledThreadPool(1);
//       ScheduledExecutorService executorService2 = Executors.newScheduledThreadPool(1);

        executorService1.scheduleAtFixedRate(()->{
            carMaintainData.choose();
            carMaintainData.send();
        },0,time, TimeUnit.HOURS);
//        executorService2.scheduleAtFixedRate(()->{
//            carMaintainData.send();
//        },0,time, TimeUnit.SECONDS);
    }


}
