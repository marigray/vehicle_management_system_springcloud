package com.dra.security.service;

import java.util.HashMap;


public interface CheckTokenService {
    boolean check(String token);

    HashMap<String,Object> getTokenMessage(String token);

    String getToken(String id,
                    String username,
                    String type);
}
