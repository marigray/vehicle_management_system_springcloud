package com.ycx.lend.web;

import com.ycx.lend.exception.ParamException;
import com.dra.pojo.msg.FormatData;
import com.ycx.lend.service.CompanyService;
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
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @RequestMapping("/setLocation.do")
    public FormatData<Object> setLocation(@RequestParam("location")String location) throws ParamException {
        int i = companyService.setCompanyLocation(location);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

}
