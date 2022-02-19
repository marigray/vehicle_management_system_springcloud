package com.dra.login.web;

import com.dra.login.service.UserMessageService;
import com.dra.pojo.login.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class UserMessageController {

    @Resource
    private UserMessageService userMessageService;
    @RequestMapping(value = "/findUser",method = RequestMethod.GET)
    public UserMessage findUser(String id, String type){
        log.info("进入findUser");
        return userMessageService.findUser(id,type);
    }
}
