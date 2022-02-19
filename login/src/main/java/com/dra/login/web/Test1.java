package com.dra.login.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;


public class Test1 {

    @Autowired()
    @Qualifier("http")
    private RestTemplate restTemplate;
    @Test
    public void test(){

        try {
            Class.forName("com.dra.controller.LoginController");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
