package com.dra.msg;


import com.dra.msg.config.WorkStart;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.Resource;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MsgSend implements CommandLineRunner {
    @Resource
    private WorkStart workStart;
    public static void main(String[] args) {

//        System.out.println();
        SpringApplication.run(MsgSend.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        workStart.run();
    }
}
