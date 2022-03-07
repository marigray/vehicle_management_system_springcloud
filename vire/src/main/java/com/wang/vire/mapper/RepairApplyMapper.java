package com.wang.vire.mapper;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.RepairApply;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/1/23 20:41
 * @Description
 */
@Repository
public interface RepairApplyMapper extends Mapper<RepairApply> {
    //通过车辆ID和申请单状态查询申请单
    List<RepairApply> queryApplyByCarIdAndApplyStatus(@Param("carId")String carId,@Param("applyStatus")int applyStatus);
    //查询所有维修申请单
    Page<RepairApply> queryAllApply();
    //通过申请人查询用户的所有申请单
    List<RepairApply> queryApplyByUser(@Param("applicantId") String applicantId);
    //查询所有 维修申请单ID
    @Select("select apply_id from repair_apply")
    List<String> queryAllApplicationId();
    //更改维修申请单状态
//    @Update("update repair_apply set apply_status = #{applyStatus} where apply_id = #{applyId}")
    int updateAuditStatus(@Param("applyId") String applyId,int applyStatus);
}
