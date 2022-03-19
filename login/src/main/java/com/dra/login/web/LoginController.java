package com.dra.login.web;

import com.dra.annotation.Public;
import com.dra.login.exception.ParamException;
import com.dra.login.service.LoginService;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
@Public
public class LoginController {

    @Resource
    private LoginService loginService;


    @RequestMapping("/user.do")
    public Object loginUser(String username,String password) throws ParamException {
        /*
          调用业务层进行验证 xxxService(id,password)
          返回jwt字符 或者 null

         */
        String s = loginService.userLogin(username, password);
        if (s==null){
            throw new ParamException("username or password is error",233);
        }

        return new ResponseFormat().getSuccess("success data is jwt",250, s);
    }

    @RequestMapping("/bg.do")
    public Object loginBackground(String username, String password) throws ParamException {
        /*
          调用业务层进行验证 xxxService(id,password)
          返回jwt字符 或者 null

         */
        String s = loginService.backgroundLogin(username, password);
        if (s==null){
            throw new ParamException("username or password is error",233);
        }

        return new ResponseFormat().getSuccess("success data is jwt",250, s);
    }
}
