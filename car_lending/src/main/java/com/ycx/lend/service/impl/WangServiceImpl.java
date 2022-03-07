package com.ycx.lend.service.impl;

import com.github.pagehelper.Page;
import com.ycx.lend.mapper.*;
import com.ycx.lend.pojo.*;
import com.ycx.lend.service.WangService;
import com.ycx.lend.utils.EmptyChecker;
import com.ycx.lend.utils.ServiceUtils;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/24 19:27
 * @@Description
 */
@Service
public class WangServiceImpl implements WangService {
    @Autowired
    AuditEndMapper auditEndMapper;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    AuditorMapper auditorMapper;
    @Autowired
    AuditStatusMapper auditStatusMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    StatusMapper statusMapper;
    @Autowired
    CarChangeMapper carChangeMapper;
    @Autowired
    ApplicationMapper applicationMapper;

    @Override
    public AuditEnd auditEndSelByKey(String auditId) {
        return auditEndMapper.selectByPrimaryKey(auditId);
    }

    @Override
    public AuditEnd auditEndSelByApplicationId(String applicationId) {
        return auditEndMapper.queryAuditEndByApplicationId(applicationId);
    }

    @Override
    public List<AuditEnd> auditEndSelByAuditorId(String auditorId) {
        return auditEndMapper.queryAuditByAuditorId(auditorId);
    }

    @Override
    public List<HashMap<String, Object>> auditEndSelNumOfEveryAuditor() {
        return auditEndMapper.queryAuditOfEndAuditor();
    }

    @Override
    public int auditEndInsertSelective(AuditEnd auditEnd) {
        return auditEndMapper.insertSelective(auditEnd);
    }

    @Override
    public int auditEndDelByKey(String auditId) {
        return auditEndMapper.deleteByPrimaryKey(auditId);
    }

    @Override
    public int auditEndUpdSelective(AuditEnd auditEnd) {
        return auditEndMapper.updateByPrimaryKeySelective(auditEnd);
    }

    @Override
    public Page<AuditEnd> auditEndSelAll() {
        return auditEndMapper.queryAllAuditEnd();
    }



    //Audit
    @Override
    public Audit auditSelByKey(String auditId) {
        return auditMapper.selectByPrimaryKey(auditId);
    }

    @Override
    public List<Audit> auditSelByAuditorId(String auditorId) {
        return auditMapper.queryAuditByAuditor(auditorId);
    }

    @Override
    public List<HashMap<String, Object>> auditSelNumByNormalAuditor() {
        return auditMapper.queryAuditOfAuditor();
    }

    @Override
    public Audit auditSelIfAllot(String auditorId, String applicationId) {
        return auditMapper.queryIfAllot(auditorId,applicationId);
    }

    @Override
    public int auditInsertSelective(Audit audit) {
        return auditMapper.insertSelective(audit);
    }

    @Override
    public HashMap<String, Object> auditSelNumByAuditorId(String auditorId) {
        return auditMapper.queryAuditOfAuditorById(auditorId);
    }

    @Override
    public HashMap<String, Object> auditSelAuditNumAllotAuditor(String applicationId) {
        return auditMapper.queryAuditorCountById(applicationId);
    }

    @Override
    public int auditDelByKey(String auditId) {
        return auditMapper.deleteByPrimaryKey(auditId);
    }

    @Override
    public int auditUpdSelective(Audit audit) {
        return auditMapper.updateByPrimaryKeySelective(audit);
    }

    @Override
    public Integer auditSelPassedCountByApplicationId(String applicationId) {
        return auditMapper.queryPassedCount(applicationId);
    }

    @Override
    public Integer auditSelNotPassedCountByApplicationId(String applicationId) {
        return auditMapper.queryNotPassCount(applicationId);
    }

    @Override
    public Page<Audit> auditSelAll() {
        return auditMapper.queryAllAudit();
    }

    @Override
    public List<Audit> auditSelByApplicationId(String applicationId) {
        return auditMapper.getAuditByApplication(applicationId);
    }

    @Override
    public int auditDelByApplicationId(String applicationId) {
        return auditMapper.delByApplicationId(applicationId);
    }


    //Auditor
    @Override
    public List<String> auditorSelAllNormal() {
        return auditorMapper.queryNormalAuditorId();
    }
    @Override
    public Auditor auditorSelByKey(String auditorId) {
        return auditorMapper.selectByPrimaryKey(auditorId);
    }

    @Override
    public List<String> queryEndAuditorId() {
        return auditorMapper.queryEndAuditorId();
    }

    @Override
    public AuditStatus auditStatusSelByKey(Integer status) {
        return auditStatusMapper.selectByPrimaryKey(status);
    }

    //Car
    @Override
    public Car carSelByKey(String carId) {
        return carMapper.selectByPrimaryKey(carId);
    }

    @Override
    public int carUpdSelective(Car car) {
        return carMapper.updateByPrimaryKeySelective(car);
    }

    //Status
    @Override
    public Status statusSelByKey(Integer statusNum) {
        return statusMapper.selectByPrimaryKey(statusNum);
    }

    @Override
    public int carChangeInsert(CarChange carChange) {
        return carChangeMapper.insert(carChange);
    }

    @Override
    public Application applicationSelByTime(String carId, String applicationTime) throws ParseException {
        List<Application> applications = applicationMapper.queryApplicationByTime(carId, ServiceUtils.StrToDate(applicationTime));
        if(EmptyChecker.isEmpty(applications)){
            return null;
        }else {
            return applications.get(0);
        }
    }
}
