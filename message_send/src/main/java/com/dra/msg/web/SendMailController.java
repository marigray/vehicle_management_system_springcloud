package com.dra.msg.web;

import com.dra.msg.exception.ParamException;
import com.dra.msg.service.MailWorkService;
import com.dra.pojo.msg.FormatData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMailController {

    @Resource
    public MailWorkService mailWorkService;

    @RequestMapping("/sendcode.do")
    public FormatData<Object> sendCheckCode(@RequestParam String to) throws Exception {
        Object o = mailWorkService.sendCheckCode(to);
        if (o==null)
            throw new ParamException("MailAddress or param is error",505);
        return new FormatData<>(null,200,(String) o);
    }


}
