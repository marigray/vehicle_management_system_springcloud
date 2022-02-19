package com.dra.security.utils;


import com.dra.pojo.login.*;
import com.dra.security.service.UserMessageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class PowerCompare {
    //远程调用
    @Resource
    private UserMessageService userMessageService;

    /**
     * 从数据库中获取用户详细信息
     * @param url 请求的url
     * @param jwt jwt解析信息
     */
    public boolean hasPower(String url,Map<String, Object> jwt) {
        UserMessage user = userMessageService.findUser((String) jwt.get("id"), (String) jwt.get("type"));
        if (user!=null){
            for (Role role : user.getRoles()) {
                for (Power power : role.getPowers()) {
                    if (url.endsWith("do")) {
                        for (Element element : power.getElements()) {
                            if (element.getElementEncode().equals(url))
                                return true;
                        }
                    } else {
                        for (File file : power.getFiles()) {
                            if (file.getFileUrl().equals(url))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
