package com.dra.login.dao;

import com.dra.pojo.login.User;
import com.dra.pojo.login.UserMessage;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
@Mapper
public interface UserMapper {
    UserMessage findUser(@Param("id")  String id);
    int add(User user);

    Page<User> searchAllUserByUsername(@Param("username") String username);

    User searchByUsername(@Param("username") String username);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(@Param("id") String id);
    User searchUserById(@Param("id") String id);

    HashMap<String,String> searchUserByUsername(@Param("username")String username);
    HashMap<String,String> searchBackgroundByUsername(@Param("username") String username);

}
