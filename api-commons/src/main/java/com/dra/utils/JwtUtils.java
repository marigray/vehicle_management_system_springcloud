package com.dra.utils;


import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


public class JwtUtils {
    private static final long time = 1000L * 60 * 60 * 24 * 30;
    private static final String security = "admin";

    public static String createToken(String id, String username, String type) {
        JwtBuilder jwtBuilder = Jwts.builder();
        return (jwtBuilder
                //头部
                .setHeaderParam("jyp", "JWT")
                .setHeaderParam("alg", "HS256")
                //载荷
                .claim("id", id)
                .claim("username", username)
                .claim("type", type)
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //签名
                .signWith(SignatureAlgorithm.HS256, security)
                .compact());
    }

    public static boolean parseToken(String token) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(security).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        Claims body = claimsJws.getBody();
        return body.get("id") != null && !body.get("id").equals("");
    }

    public static HashMap<String, Object> getTokenMessage(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(security).parseClaimsJws(token);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id", claimsJws.getBody().get("id"));
        stringStringHashMap.put("username", claimsJws.getBody().get("username"));
        stringStringHashMap.put("type", claimsJws.getBody().get("type"));
        return stringStringHashMap;
    }

    @Test
    public void TEST() {
        System.out.println(createToken("223", "admin","background"));
//        HashMap<String, Object> tokenMessage = getTokenMessage(
//                "\"eyJqeXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJ1c2VybmFtZSI6InJvb3QiLCJ0eXBlIjoiYmFja2dyb3VuZCIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2MzUzMDQxNzIsImp0aSI6ImFiZmM2YmY1LTE3MDAtNDcwMi04MWM3LWQ3ODFmZTI5MzQyNSJ9.BgKCsJaHvoeXkw4eYadr1YhKazKToZJi3fJoc_6y8SA\"");
//        System.out.println(tokenMessage);

    }
}
