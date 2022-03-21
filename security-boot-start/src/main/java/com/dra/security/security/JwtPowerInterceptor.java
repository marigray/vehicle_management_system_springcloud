package com.dra.security.security;

import com.dra.security.service.CheckTokenService;
import com.dra.security.utils.PowerCompare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 主要负责
 * 获取Jwt详细信息
 * 并从数据库内获取相关权限信息
 * 并与本次请求的路径所需权限进行比较
 * <p>
 * Controller中的方法名称不能重复
 */
@Component
@Slf4j
public class JwtPowerInterceptor {

    @Resource
    private PowerCompare powerCompare;

    @Resource
    private CheckTokenService checkTokenService;

    private final String MESSAGE = "ERROR Permission denied";


    public String preHandle(Map<String, String> map) throws Exception {
        log.info("进入JwtPowerInterceptor");
        if (Boolean.parseBoolean(map.get("isPublic")))
            return String.valueOf(true);

        //        System.out.println("==================JwtPowerInterceptor已拦截===============");
        HashMap<String, Object> jwt = checkTokenService.getTokenMessage(map.get("Jwt"));
        System.out.println("jwt :" + jwt);
        log.info("无身份信息检测");
        //无身份信息
        if (jwt.get("type") == null) {
            return MESSAGE;
        }
        log.info("普通用户身份检测");
        //普通用户身份
        if (jwt.get("type").equals("user")) {
            if (Boolean.parseBoolean(map.get("isUser"))) {
                return String.valueOf(true);
            } else {
                return MESSAGE;
            }

        }
        log.info("后台用户身份检测");
        //后台用户身份
        if (Boolean.parseBoolean(map.get("isUser"))) {
            return String.valueOf(true);
        }
        log.info("其他情况检测");
        //其他情况 需要确定当前用户是否具有权限访问非公开非用户接口
        if (!powerCompare.hasPower(map.get("url"), jwt)) {
            return MESSAGE;
        }

        return String.valueOf(true);
    }
//    @Test
//    public void te(){
//        Class<String> stringClass = String.class;
//        Method[] declaredMethods = stringClass.getDeclaredMethods();
//        for (Method declaredMethod : declaredMethods) {
//            System.out.println(declaredMethod.getName());
//        }
//    }
}
