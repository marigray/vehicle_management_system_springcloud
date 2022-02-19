package com.dra.login.service.impl;


import com.dra.login.dao.UserMapper;
import com.dra.login.service.CheckTokenService;
import com.dra.login.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{

    @Resource
    private CheckTokenService checkTokenService;

    @Resource
    private UserMapper userMapper;

    /**
     * 用户名及密码判空
     * @param username
     * @param password
     * @return
     */
    private String make1(String username,String password){
        if (username==null||username.equals("")||
        password==null||password.equals("")){
            return null;
        }
        return "true";
    }
    private String make2(String password, Map<String,String> stringStringHashMap,String type){


        System.out.println(stringStringHashMap);
        if (stringStringHashMap==null){
            return null;
        }
        if (!stringStringHashMap.get("password").equals(password)){
            return null;
        }
        return checkTokenService.getToken(stringStringHashMap.get("id"),stringStringHashMap.get("username"),type);

    }

    @Override
    public String userLogin(String username, String password) {
        if (make1(username,password)==null){
            return null;
        }

        return make2(password, userMapper.searchUserByUsername(username),"user");
    }

    @Override
    public String backgroundLogin(String username, String password) {
        if (make1(username,password)==null){
            return null;
        }

        return make2(password, userMapper.searchBackgroundByUsername(username),"background");
    }


}
