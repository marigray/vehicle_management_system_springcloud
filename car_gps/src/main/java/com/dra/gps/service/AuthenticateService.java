package com.dra.gps.service;

import com.dra.pojo.msg.FormatData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 调用远程鉴权
 */
@FeignClient(value = "SECURITY-BOOT-START")
public interface AuthenticateService {
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    FormatData<Boolean> auth(@RequestBody Map<String, String> map);
}
