package com.dra.security.web;

import com.dra.pojo.msg.FormatData;
import com.dra.security.security.JwtCheckInterceptor;
import com.dra.security.security.JwtFindInterceptor;
import com.dra.security.security.JwtPowerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Slf4j
public class AuthenticateController {
    @Resource
    private JwtFindInterceptor jwtFindInterceptor;
    @Resource
    private JwtCheckInterceptor jwtCheckInterceptor;
    @Resource
    private JwtPowerInterceptor jwtPowerInterceptor;

    /**
     * 鉴权入口
     * @param map
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public FormatData<Boolean> auth(@RequestBody Map<String, String> map) {
        System.out.println(map);
        try {
            //调用find
            String s1 = jwtFindInterceptor.preHandle(map);

            if (!s1.equals("true")) {
                return new FormatData<>(false, 401, s1);
            }
            //调用check
            String s2 = jwtCheckInterceptor.preHandle(map);
            if (!s2.equals("true")) {
                return new FormatData<>(false, 402, s2);
            }
            //调用power
            String s3 = jwtPowerInterceptor.preHandle(map);
            if (!s3.equals("true")) {
                return new FormatData<>(false, 403, s3);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return new FormatData<>(false, 501, "发生异常，异常信息为:" + e.getMessage());
        }
        return new FormatData<>(true,200,"success");
    }

}
