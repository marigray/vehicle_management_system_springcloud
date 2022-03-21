package com.dra.login.service.impl;

import com.dra.login.dao.BackgroundMapper;
import com.dra.login.dao.UserMapper;
import com.dra.login.service.UserMessageService;
import com.dra.pojo.login.UserMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BackgroundMapper backgroundMapper;


    @Override
    public UserMessage findUser(String id, String type) {
        if (type.equals("user"))
            return userMapper.findUser(id);
        return backgroundMapper.findUser(id);
    }

//    @Test
//    public void test(){
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("applicationContext-tx.xml");
//        UserMessageServiceImpl checkUserServiceImpl = (UserMessageServiceImpl) context.getBean("userMessageServiceImpl");
//        System.out.println(checkUserServiceImpl.findUser("1","background"));




//    }
}
