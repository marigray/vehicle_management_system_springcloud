package com.wang.vire;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.wang.vire.mapper"})
@EnableEurekaClient
@EnableFeignClients
public class vireApplication {
    public static void main(String[] args) {
        SpringApplication.run(vireApplication.class,args);
    }
}
