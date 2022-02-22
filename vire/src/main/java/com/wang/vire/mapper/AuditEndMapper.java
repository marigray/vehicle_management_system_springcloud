package com.wang.vire.mapper;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.AuditEnd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/1/22 22:15
 * @Description
 */
@Repository
public interface AuditEndMapper extends Mapper<AuditEnd> {
    @Select("select * from audit_end where auditor_id=#{auditorId}")
    List<AuditEnd> queryAuditByAuditorId(@Param("auditorId") String auditorId);

    @Select("select * from audit_end where application_id=#{applicationId}")
    AuditEnd queryAuditEndByApplicationId(@Param("applicationId") String applicationId);

    @Select("select * from audit_end")
    Page<AuditEnd> queryAllAuditEnd();

    //查询每个终审员被分配多少申请单
    List<HashMap<String, Object>> queryAuditOfEndAuditor();
    //查询每个申请到对应的终审表的状态
    @Select("select end_status from audit_end where application_id=#{applicationId}")
    int queryStatusByApplicationId(@Param("applicationId")String applicationId);
    int delByApplicationId(@Param("application")String applicationId);
    @Select("select * from audit_end where application_id=#{applicationId}")
    AuditEnd queryByApplicationId(@Param("applicationId")String applicationId);
}
