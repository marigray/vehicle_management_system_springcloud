package com.dra.login.dao;

import com.dra.pojo.login.Background;
import com.dra.pojo.login.UserMessage;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

@Mapper
public interface BackgroundMapper {
    UserMessage findUser(@Param("id") String id);
    int addBackground(Background background);
    int updateBackground(Background background);
    int deleteBackground(@Param("id") String id);
    HashMap<String,String> searchBackgroundById(@Param("id") String id);
    HashMap<String,String> searchBackgroundByUsername(@Param("username") String Username);
    Page<HashMap<String,String>> searchAllBackgroundByUsername(@Param("username") String username);
}
