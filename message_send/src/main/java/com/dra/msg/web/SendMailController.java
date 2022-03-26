package com.dra.msg.web;

import com.dra.annotation.Public;
import com.dra.msg.exception.ParamException;
import com.dra.msg.service.MailWorkService;
import com.dra.pojo.msg.FormatData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Public
@RequestMapping("/send")
public class SendMailController {

    @Resource
    public MailWorkService mailWorkService;

    @RequestMapping("/sendcode.do")
    public FormatData<Object> sendCheckCode(@RequestParam String to) throws Exception {
        log.info("进入SENDCODE控制器");
        Object o = mailWorkService.sendCheckCode(to);

        if (o==null)
            throw new ParamException("MailAddress or param is error",505);
        return new FormatData<>(null,200,(String) o);
    }


}
