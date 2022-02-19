package com.dra.security.service;

import com.dra.pojo.login.UserMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "LOGIN")
public interface UserMessageService {
    @RequestMapping(value = "/findUser",method = RequestMethod.GET)
    UserMessage findUser(@RequestParam("id") String id
            , @RequestParam("type") String type);
}
