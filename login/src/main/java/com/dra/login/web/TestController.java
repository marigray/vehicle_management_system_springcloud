package com.dra.login.web;

import com.dra.annotation.Public;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
@Public
@Slf4j
public class TestController {



    @RequestMapping("/test.do")
    public String test(HttpServletRequest httpServletRequest,String name){


        log.info("jwt:"+httpServletRequest.getHeader("jwt"));

        return "ok";
    }
}
