package com.wang.vire.mapper;

import com.wang.vire.pojo.RepairApply;
import com.wang.vire.pojo.Repairer;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/18 21:43
 * @Description
 */
@Repository
public interface RepairerMapper extends Mapper<Repairer> {
    //查询所有 维修申请单ID
    @Select("select * from repair where apply_id=#{applyId}")
    List<String> queryByApplyId(@Param("applyId") String applyId);
}
