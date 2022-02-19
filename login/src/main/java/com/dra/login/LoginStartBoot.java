package com.dra.login;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.dra.login.dao")
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients
public class LoginStartBoot {
    public static void main(String[] args) {
        SpringApplication.run(LoginStartBoot.class,args);
    }
}
