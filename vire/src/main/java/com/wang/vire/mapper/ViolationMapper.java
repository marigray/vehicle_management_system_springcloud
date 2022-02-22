package com.wang.vire.mapper;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.Violation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/10 11:36
 * @Description
 */

@Repository
public interface ViolationMapper extends Mapper<Violation> {
    @Select("select * from violation")
    Page<Violation> queryAllViolation();
    @Select("select * from violation where violator_id=#{violatorId}")
    List<Violation> queryByUserId(@Param("violatorId")String violatorId);
}
