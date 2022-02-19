package com.dra.security.service.impl;

import com.dra.security.service.CheckTokenService;
import com.dra.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CheckTokenServiceImpl implements CheckTokenService {
    @Override
    public boolean check(String token) {
        return JwtUtils.parseToken(token);
    }

    @Override
    public HashMap<String,Object> getTokenMessage(String token){
        return JwtUtils.getTokenMessage(token);
    }

    @Override
    public String getToken(String id,
                           String username,
                           String type) {

        return JwtUtils.createToken(id,username,type);
    }
}
