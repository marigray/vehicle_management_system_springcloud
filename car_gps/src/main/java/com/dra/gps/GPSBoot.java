package com.dra.gps;


import com.dra.gps.config.ApplicationStartConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.dra.gps.mapper")
@ServletComponentScan
@EnableFeignClients
public class GPSBoot implements CommandLineRunner{

    @Resource
    private ApplicationStartConfig applicationStartConfig;

    public static void main(String[] args) {
        SpringApplication.run(GPSBoot.class,args);

    }

    @Override
    public void run(String... args) throws Exception {
        applicationStartConfig.run();
    }


}
