package com.ycx.lend.mapper;

import com.github.pagehelper.Page;
import com.ycx.lend.pojo.AuditEnd;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/25 12:37
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

    @Delete("delete from audit_end where application_id=#{applicationId}")
    int delByApplicationId(@Param("applicationId")String applicationId);
}
