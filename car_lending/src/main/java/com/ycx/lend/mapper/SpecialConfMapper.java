package com.ycx.lend.mapper;

import com.ycx.lend.pojo.SpecialConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * @Author ycx
 * @Date 2022/2/21 14:47
 * @Description
 */
@Component
public interface SpecialConfMapper extends Mapper<SpecialConfig> {

    @Update("update special_conf set value=#{location} where `key`='companyLocation'")
    int setCompanyLocation(@Param("location") String location);

    @Update("update special_conf set value=#{jwt} where `key`='jwt'")
    int setJwt(@Param("jwt") String location);

    @Select("select value from special_conf where `key`='companyLocation'")
    String getCompanyLocation();

    @Select("select value from special_conf where `key`='jwt'")
    String getJwt();
}
