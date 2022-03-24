package com.ycx.lend.service;

import com.ycx.lend.exception.ParamException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ycx
 * @Date 2022/2/23 19:54
 * @Description
 */
@FeignClient(value = "CAR-GPS")
public interface GPSLogService {
    @RequestMapping("/gps_log/search.do")
    public Object search(@RequestParam("carId") String carId, @RequestParam("date1") long date1, @RequestParam("date2") long date2,
                         @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestHeader(name = "jwt", required = true) String jwt) throws ParamException;
}
