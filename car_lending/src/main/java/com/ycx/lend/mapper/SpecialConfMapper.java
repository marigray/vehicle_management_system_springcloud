package com.ycx.lend.mapper;

import com.ycx.lend.pojo.SpecialConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * @Author ycx
 * @Date 2022/2/21 14:47
 * @Description
 */
@Resource
public interface SpecialConfMapper extends Mapper<SpecialConfig> {
    @Update("update special_conf set value=#{location} where `key`='companyLocation'")
    int setCompanyLocation(@Param("location") String location);

    @Select("select value from special_conf where `key`='companyLocation'")
    String getCompanyLocation();
}
