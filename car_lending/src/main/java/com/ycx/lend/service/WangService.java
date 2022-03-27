package com.ycx.lend.service;


import com.github.pagehelper.Page;
import com.ycx.lend.pojo.*;
import io.lettuce.core.dynamic.annotation.Param;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/24 19:20
 * @Description
 */
public interface WangService {

    //AuditEnd
    AuditEnd auditEndSelByKey( String auditId);
    AuditEnd auditEndSelByApplicationId(String applicationId);
    List<AuditEnd> auditEndSelByAuditorId(String auditorId);
    List<HashMap<String, Object>> auditEndSelNumOfEveryAuditor();
    String queryLessNumAudit();
    int auditEndInsertSelective(AuditEnd auditEnd);
    int auditEndDelByKey(String auditId);
    int auditEndUpdSelective(AuditEnd auditEnd);
    Page<AuditEnd> auditEndSelAll();


    //Audit
    Audit auditSelByKey(String auditId);
    List<Audit> auditSelByAuditorId(String auditorId);
    List<HashMap<String, Object>> auditSelNumByNormalAuditor();
    Audit auditSelIfAllot(String auditorId,String applicationId);
    int auditInsertSelective(Audit audit);
    HashMap<String, Object> auditSelNumByAuditorId(String auditorId);
    HashMap<String, Object> auditSelAuditNumAllotAuditor(String applicationId);
    int auditDelByKey(String auditId);
    int auditUpdSelective(Audit audit);
    Integer auditSelPassedCountByApplicationId(String applicationId);
    Integer auditSelNotPassedCountByApplicationId(String applicationId);
    Page<Audit> auditSelAll();
    List<Audit> auditSelByApplicationId(String applicationId);
    int auditDelByApplicationId(String applicationId);




    //Auditor
    Auditor auditorSelByKey(String auditorId);
    List<String> queryEndAuditorId();
    List<String> auditorSelAllNormal();

    //AuditStatus
    AuditStatus auditStatusSelByKey(Integer status);


    //Car
    Car carSelByKey(String carId);
    int carUpdSelective(Car car);

    //Status
    Status statusSelByKey(Integer statusNum);

    //CarChange
    int carChangeInsert(CarChange carChange);

    //Application
    Application applicationSelByTime(String carId,String applicationTime) throws ParseException;


}
