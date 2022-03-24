package com.ycx.lend.web;

import com.ycx.lend.exception.ParamException;
import com.dra.pojo.msg.FormatData;
import com.ycx.lend.service.ConfigService;
import com.ycx.lend.utils.WebCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ycx
 * @Date 2022/2/19 12:38
 * @Description
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    ConfigService configService;

    @RequestMapping("/setLocation.do")
    public FormatData<Object> setLocation(@RequestParam("location")String location) throws ParamException {
        int i = configService.setCompanyLocation(location);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    @RequestMapping("/setJwt.do")
    public FormatData<Object> setJwt(@RequestParam("jwt")String jwt) throws ParamException {
        int i = configService.setJwt(jwt);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }
}
