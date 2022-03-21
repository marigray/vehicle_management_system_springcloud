package com.dra.login.dao;

import com.dra.pojo.login.Power;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
@Mapper
public interface PowerMapper {
    Page<Power> findPower(@Param("roleId") String roleId);

    int addPower(Power power);
    int updatePower(Power power);
    int deletePower(@Param("powerId") String powerId);
    Power searchPowerById(@Param("powerId") String powerId);
    ArrayList<Power> searchAllPowerByUsername();
}
