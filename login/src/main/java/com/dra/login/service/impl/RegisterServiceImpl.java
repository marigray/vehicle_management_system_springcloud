package com.dra.login.service.impl;

import com.dra.login.dao.BackgroundMapper;
import com.dra.login.dao.UserMapper;
import com.dra.login.service.RegisterService;
import com.dra.pojo.login.Background;
import com.dra.pojo.login.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private BackgroundMapper backgroundMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public int registerUser(User user) {
        if (user == null ||
                user.getUsername() == null || user.getUsername().equals("") ||
                user.getPassword() == null || user.getPassword().equals(""))
            return 0;
        String id;
        do {
            id = "1" + new Date().getTime();
        } while (userMapper.findUser(id) != null);
        user.setId(id);
        return userMapper.addUser(user);
    }

    @Override
    public int registerBackground(Background background) {
        if (background == null ||
                background.getUsername() == null || background.getUsername().equals("") ||
                background.getPassword() == null || background.getPassword().equals(""))
            return 0;

        String id;
        do {
            id = "2" + new Date().getTime();
        } while (backgroundMapper.findUser(id) != null);
        background.setId(id);
        return backgroundMapper.addBackground(background);
    }

//    @Test
//    public void te() {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-tx.xml");
//        RegisterServiceImpl registerServiceImpl = (RegisterServiceImpl) context.getBean("registerServiceImpl");
////        User user = new User();
////        user.setUsername("天下无双");
////        user.setPassword("123");
////        registerServiceImpl.registerUser(user);
////        Background background = new Background();
////        background.setUsername("会计员小陈");
////        background.setPassword("123");
////        registerServiceImpl.registerBackground(background);
//    }
}
