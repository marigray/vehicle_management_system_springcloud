package com.dra.msg.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class MsgAppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
