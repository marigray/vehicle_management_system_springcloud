package com.dra.login.web;


import com.dra.annotation.Public;
import com.dra.login.exception.ParamException;
import com.dra.login.redis.RedisUtil;
import com.dra.login.service.RegisterService;
import com.dra.pojo.login.Background;
import com.dra.pojo.login.User;
import com.dra.utils.FinalValueSet;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/reg")
public class RegisterController {

    private final String REGISTER_SUCCESS = "注册成功";

    @Resource
    private RegisterService registerService;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "/user.do",method = RequestMethod.POST)
    @Public
    public Object userReg(@RequestBody User user, @RequestParam String code) throws ParamException {
        //检测验证码
        String string = redisUtil.getString(FinalValueSet.CH_CODE+":"+user.getEMail(),0);
        System.out.println("string="+string+"\ncode:"+code);
        if (!code.equals(string))
            throw new ParamException("验证码有误",403);
        System.out.println("验证码检测完毕");
        int i = registerService.registerUser(user);
        if (i==0){
            throw new ParamException();
        }

        return new ResponseFormat().getSuccess(REGISTER_SUCCESS,200,true);
    }

    @RequestMapping(value = "/bg.do",method = RequestMethod.POST)
    public Object backgroundReg(@RequestBody Background background) throws ParamException {
        int i = registerService.registerBackground(background);
        if (i==0){
            throw new ParamException();
        }

        return new ResponseFormat().getSuccess(REGISTER_SUCCESS,200,true);
    }
}
