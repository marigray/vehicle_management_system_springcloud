package com.ycx.lend.service.impl;

import com.ycx.lend.mapper.SpecialConfMapper;
import com.ycx.lend.service.ConfigService;
import com.ycx.lend.utils.EmptyChecker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author ycx
 * @Date 2022/2/19 12:40
 * @Description
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    SpecialConfMapper specialConfMapper;

    @Override
    public int setCompanyLocation(String Location) {
        if (EmptyChecker.isEmpty(Location)) {
            return 0;
        }
        return specialConfMapper.setCompanyLocation(Location);
    }

    @Override
    public HashMap<String, Double> getCompanyLocation() {
        HashMap<String, Double> hashMap = new HashMap<>();
        String companyLocation = specialConfMapper.getCompanyLocation();
        String[] split = companyLocation.split(",");
        Double i1 = Double.valueOf(split[0]);
        hashMap.put("longitude", i1);
        Double i2 = Double.valueOf(split[1]);
        hashMap.put("latitude", i2);
        return hashMap;
    }

    @Override
    public int setJwt(String jwt) {
        if (EmptyChecker.isEmpty(jwt)) {
            return 0;
        }
        int i = specialConfMapper.setJwt(jwt);
        if (i < 0) {
            return -4;
        }
        return i;
    }
}
