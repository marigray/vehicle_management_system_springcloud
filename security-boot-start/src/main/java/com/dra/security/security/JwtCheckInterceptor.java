package com.dra.security.security;

import com.dra.security.service.CheckTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 主要负责
 * 检测Jwt相关是否符合标准
 */
@Component
@Slf4j
public class JwtCheckInterceptor {

    @Resource
    private CheckTokenService checkTokenService;

    private final String MESSAGE = "jwt is invalid";


    public String preHandle(Map<String,String> map) throws Exception {
        log.info("进入JwtCheckInterceptor");
        if (Boolean.parseBoolean(map.get("isPublic")))
            return String.valueOf(true);


//        System.out.println("==================JwtCheckInterceptor已拦截===============");
        String jwt = map.get("Jwt");
        if (!checkTokenService.check(jwt)) {
            return MESSAGE;
        }
//        if (!JwtUtils.parseToken(jwt)) {
//
//            response.getWriter().print(s);
//            return false;
//        }
        return String.valueOf(true);
    }
}
