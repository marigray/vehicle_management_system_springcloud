package com.ycx.lend.mapper;

import com.github.pagehelper.Page;
import com.ycx.lend.pojo.Audit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

@Repository
public interface AuditMapper extends Mapper<Audit> {

    //查询每个普通审核员被分配多少申请单
    List<HashMap<String, Object>> queryAuditOfAuditor();

    //查询特定审核员被分配多少申请单
    HashMap<String, Object> queryAuditOfAuditorById(@Param("auditorId") String auditorId);

    //查询特定审核单被分配给多少审核员
    HashMap<String, Object> queryAuditorCountById(@Param("applicationId") String applicationId);

    List<Audit> queryAuditByUser(@Param("userId") String userId);

    //查询指定审核员的审核单
    List<Audit> queryAuditByAuditor(@Param("auditorId") String auditorId);

    //查找申请单对应审核单通过的数量
    Integer queryPassedCount(@Param("applicationId") String applicationId);

    //查找申请单对应审核单不通过的数量
    Integer queryNotPassCount(@Param("applicationId") String applicationId);

    List<Audit> getAuditByApplication(@Param("applicationId")String applicationId);

    //查询审核单是否已被分配给该审核员
    @Select("select * from audit where auditor_id=#{auditorId} and application_id=#{applicationId}")
    Audit queryIfAllot(@Param("auditorId") String auditorId, @Param("applicationId") String applicationId);

    @Select("select * from audit where auditor_id=#{auditorId}")
    List<Audit> queryAuditByAuditorId(@Param("auditorId") String auditorId);

    @Select("select * from audit")
    Page<Audit> queryAllAudit();

    @Delete("delete from audit where application_id=#{applicationId}")
    int delByApplicationId(@Param("applicationId") String applicationId);

}
