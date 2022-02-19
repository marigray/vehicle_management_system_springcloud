package com.dra.login.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {



    @RequestMapping("/test.do")

    public String test(String name){


        System.out.println("===============已进入TestController===============");

        return "ok";
    }
}
