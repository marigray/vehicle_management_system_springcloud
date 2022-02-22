package com.wang.vire.mapper;


import com.dra.pojo.login.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author wang
 * @Data 2022/2/13 16:17
 * @Description
 */
@Repository
public interface UserMapper extends Mapper<User> {
}
