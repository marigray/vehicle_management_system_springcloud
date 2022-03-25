package com.dra.msg.web;

import com.dra.msg.exception.ParamException;
import com.dra.msg.service.PushMailService;
import com.dra.pojo.msg.FormatData;
import com.dra.pojo.msg.PushMail;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pushmail")
public class PushMailController {

    @Resource
    private PushMailService pushMailService;

    @RequestMapping(value = "/addpushmail.do",method = RequestMethod.POST)
    public FormatData<Object> addPushMail(PushMail pushMail) throws ParamException {
        int i = pushMailService.addPushMail(pushMail);
        if (i==0)
            throw new ParamException("参数有误",402);
        return new FormatData<>(i,200,"success");
    }

    @RequestMapping(value = "/delpushmail.do",method = RequestMethod.GET)
    public FormatData<Object> delPushMail(String id) throws ParamException {
        int i = pushMailService.delPushMail(id);
        if (i==0)
            throw new ParamException("参数有误",402);
        return new FormatData<>(i,200,"success");
    }

    @RequestMapping(value = "/uppushmail.do",method = RequestMethod.POST)
    public FormatData<Object> updatePushMail(PushMail pushMail)  throws ParamException {

        int i = pushMailService.updatePushMail(pushMail);
        if (i==0)
            throw new ParamException("参数有误",402);
        return new FormatData<>(i,200,"success");
    }

    @RequestMapping(value = "/selpushmail.do",method = RequestMethod.GET)
    public FormatData<Object> selectAll() {
        return new FormatData<>(pushMailService.selectAll(),200,"success");
    }
}
