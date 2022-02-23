package com.ycx.lend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author ycx
 * @Date 2022/2/22 19:22
 * @Description
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.ycx.lend.mapper"})
@ServletComponentScan
@EnableFeignClients
public class LendingBoot {
    public static void main(String[] args) {
        SpringApplication.run(LendingBoot.class,args);
    }
}
