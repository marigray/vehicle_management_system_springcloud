package com.dra.login.interceptors;

import com.alibaba.fastjson.JSON;
import com.dra.login.service.AuthenticateService;
import com.dra.pojo.msg.FormatData;
import com.dra.utils.AuthAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        log.info("进入拦截器");
        log.info(handler.toString());
        response.setContentType("application/json;charset=UTF-8");
        Map<String, String> annotationMessage = AuthAnnotation.getAnnotationMessage(request, response, handler);
        log.info("推出拦截器");
        FormatData<Boolean> auth = authenticateService.auth(annotationMessage);

        if (!auth.getData()){

            response.getWriter().println(JSON.toJSONString(new FormatData<>(null,auth.getCode(),auth.getMsg())));
        }

        return auth.getData();
    }
}
