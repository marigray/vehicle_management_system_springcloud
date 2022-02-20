package com.dra;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient //开启服务注册中心客户端
@EnableFeignClients // 开启远程连接客户端
public class test {
    public static void main(String[] args) {
        SpringApplication.run(test.class,args);
    }
}
