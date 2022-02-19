package com.dra.security.security;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 此处负责寻找
 * header中是否拥有jwt信息
 * 找到并对其判空
 */
@Component
public class JwtFindInterceptor {

    private final String MESSAGE = "NotFund the Jwt,log in first,please";

    public String preHandle(Map<String,String> map) throws Exception {

        if (Boolean.parseBoolean(map.get("isPublic")))
            return String.valueOf(true);

//        System.out.println("===================JwtFindInterceptor已拦截===============");
        String jwt = map.get("Jwt");
        if (jwt == null || jwt.equals("")) {

            return MESSAGE;
        }

        return String.valueOf(true);
    }
}
