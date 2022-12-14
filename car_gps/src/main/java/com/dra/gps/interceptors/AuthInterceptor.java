package com.dra.gps.interceptors;

import com.alibaba.fastjson.JSON;
import com.dra.gps.service.AuthenticateService;
import com.dra.pojo.msg.FormatData;
import com.dra.utils.AuthAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截并进入鉴权
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private AuthenticateService authenticateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("");
        log.info("handler:"+handler.toString());
        response.setContentType("application/json;charset=UTF-8");
        FormatData<Boolean> auth = authenticateService.auth(AuthAnnotation.getAnnotationMessage(request, response, handler));
        if (!auth.getData()){
            response.getWriter().println(JSON.toJSONString(new FormatData<>(null,auth.getCode(),auth.getMsg())));
        }
        return auth.getData();
    }


}
