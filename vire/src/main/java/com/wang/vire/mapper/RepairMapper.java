package com.wang.vire.mapper;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.Repair;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/13 16:25
 * @Description
 */
@Repository
public interface RepairMapper extends Mapper<Repair> {
    Page<Repair> queryByCarId(@Param("carId")String carId);
    Page<Repair> queryByUserId(@Param("repairerId")String repairerId);
    Page<Repair> queryAllRepair();
    @Select("select * from repair where apply_id=#{applyId}")
    Page<Repair> queryByApplyId(@Param("applyId")String applyId);
}
