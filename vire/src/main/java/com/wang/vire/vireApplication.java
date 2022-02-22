package com.wang.vire;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.wang.vire.mapper"})
public class vireApplication {
    public static void main(String[] args) {
        SpringApplication.run(vireApplication.class,args);
    }
}
