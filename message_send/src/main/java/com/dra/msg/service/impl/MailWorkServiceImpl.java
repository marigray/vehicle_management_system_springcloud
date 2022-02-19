package com.dra.msg.service.impl;

import com.dra.msg.config.Mail;
import com.dra.msg.redis.RedisUtil;
import com.dra.msg.service.MailWorkService;
import com.dra.utils.CheckTool;
import com.dra.utils.FinalValueSet;
import com.dra.utils.IsNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MailWorkServiceImpl implements MailWorkService {

    @Resource
    private Mail mail;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public Object sendCheckCode(String recipientAddress) throws Exception {
//        ArrayList<Integer> list = new ArrayList<>();
        String [] data = {recipientAddress};
        if (new IsNull(data).check())
            return null;
        //验证邮箱正确性
        if (!CheckTool.Mail(recipientAddress))
            return null;
        //生成6位验证码
        int max=999999,min=100000;
        int ran2 = (int) (Math.random()*(max-min)+min);
        //并存入redis 并指定有效时间
        redisUtil.setString(FinalValueSet.CH_CODE+":"+recipientAddress,String.valueOf(ran2),15*60,1);
        //发送验证码
        boolean is;
        is = mail.send1("<h1>" + ran2 + "</h1>" +
                "\n<h1>验证码15分钟内有效,请不要把验证码泄露给其他人</h1>", "您的验证码", recipientAddress);
        if (!is)
            return null;
        return FinalValueSet.OK_STAT;
    }

}
