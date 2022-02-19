package com.dra.utils;

import com.dra.reflection.GetAnnotationValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthAnnotation {
    public static Map<String, String> getAnnotationMessage(HttpServletRequest request, HttpServletResponse response, Object handler) throws ClassNotFoundException, NoSuchMethodException {
        HashMap<String, String> map = new HashMap<>();

        GetAnnotationValue getAnnotationValue = new GetAnnotationValue();
        //获取是否公开
        map.put("isPublic", String.valueOf(getAnnotationValue.isPublic(handler)));
        //获取是否为用户
        map.put("isUser", String.valueOf(getAnnotationValue.isPublic(handler)));
        //获取url
        map.put("url", getAnnotationValue.getRequestUrl(request, handler));
        //jwt
        map.put("Jwt",request.getHeader("jwt"));
        return map;
    }
}
