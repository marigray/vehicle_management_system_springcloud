package com.ycx.lend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycx.lend.mapper.*;
import com.ycx.lend.pojo.Audit;
import com.ycx.lend.service.AuditEndService;
import com.ycx.lend.service.AuditService;
import com.ycx.lend.service.IdService;
import com.ycx.lend.utils.EmptyChecker;
import com.ycx.lend.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @Author ycx
 * @Date 2022/1/24 13:40
 * @Description
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    AuditEndMapper auditEndMapper;
    @Autowired
    AuditorMapper auditorMapper;
    @Autowired
    AuditStatusMapper auditStatusMapper;
    @Autowired
    IdService idService;
    @Autowired
    AuditEndService auditEndService;

    //派审核单到不同审核员，平均分配
    @Override
    public int allotAudit(String applicationId) {
        Audit audit = new Audit();
        if (EmptyChecker.isEmpty(applicationId)) {
            return 0;
        }
        //判断申请编号是否存在于申请表
        if (EmptyChecker.isEmpty(applicationMapper.selectByPrimaryKey(applicationId))) {
            return -2;
        }
        audit.setApplicationId(applicationId);

        //创建主键
        audit.setAuditId(ServiceUtils.SetPrimaryKey(idService.getMaxId("audit", "audit_id")));

        HashMap<String, Object> h1 = new HashMap<>();
        h1.put("auditCount", 1000000);
        //查找没有任务的审核员 分配审核
        for (String s : auditorMapper.queryNormalAuditorId()) {
            List<Audit> audit1 = auditMapper.queryAuditByAuditorId(s);
            if (EmptyChecker.isEmpty(audit1)) {
                h1.put("auditorId", s);
                break;
            }
        }
        //查找任务最少的审核员 分配审核
        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
            for (HashMap<String, Object> hashMap : auditMapper.queryAuditOfAuditor()) {
                String auditorId = (String) hashMap.get("auditorId");
                //判断该审核员是否已经被分配该申请单
                if (EmptyChecker.isEmpty(auditMapper.queryIfAllot(auditorId, applicationId))) {
                    int auditCount = ServiceUtils.NumberToInt(hashMap.get("auditCount"));
                    if (auditCount < (Integer) h1.get("auditCount")) {
                        h1.put("auditorId", auditorId);
                        h1.put("auditCount", auditCount);
                    }
                }
            }
        }
        //如果没有审核员信息被存入，那么随机分配给一个普通审核员
        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
            while (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(h1.get("auditorId")))) {
                Random random = new Random();
                int i = Integer.parseInt(idService.getMinId("auditor", "auditor_id"));
                String auditorId = String.valueOf(random.nextInt(Integer.parseInt(idService.getMaxId("auditor", "auditor_id")) - i) + i);
                if (auditorMapper.selectByPrimaryKey(auditorId).getAuditorType() == 0) {
                    h1.put("auditorId", auditorId);
                }
            }
        }
        //过滤完成，进行赋值
        audit.setAuditorId((String) h1.get("auditorId"));
        return auditMapper.insertSelective(audit);
    }

    @Override
    public int allotAllAudits() {
        int i = 1;
        int auditCount;
        for (int j = 0; j < 3; j++) {
            for (String s : applicationMapper.queryAllApplicationId()) {
                //判断有无值，无值时设置计数为0，防止空指针
                if (EmptyChecker.notEmpty(auditMapper.queryAuditOfAuditorById(s))) {
                    //防止冗余分配
                    HashMap<String, Object> countMap = auditMapper.queryAuditorCountById(s);
                    //判空
                    if (countMap == null) {
                        auditCount = 0;
                    } else auditCount = ServiceUtils.NumberToInt(countMap.get("applicationCount"));
                } else auditCount = 0;
                if (auditCount < 3)
                    i = allotAudit(s);
                if (i <= 0) {
                    return i;
                }
            }
        }
        return i;
    }

    @Override
    public int delAudit(String auditId) {
//        判空
        if (EmptyChecker.isEmpty(auditId)) {
            return 0;
        }
//        操作对象判定
        if (EmptyChecker.isEmpty(auditMapper.selectByPrimaryKey(auditId))) {
            return -3;
        }
        return auditMapper.deleteByPrimaryKey(auditId);
    }

    @Override
    public int updateAuditor(String auditId, String auditorId) {
        if (EmptyChecker.isAnyOneEmpty(auditId, auditorId)) {
            return 0;
        }
        Audit audit = new Audit();
        if (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(auditorId))) {
            return -2;
        }
        if (EmptyChecker.isEmpty(auditMapper.selectByPrimaryKey(auditId))) {
            return -3;
        }
        audit.setAuditId(auditId);
        audit.setAuditorId(auditorId);
        return auditMapper.updateByPrimaryKeySelective(audit);
    }

    @Override
    public int changeStatus(String auditId, Integer status, String changeTime) throws ParseException {
        if (EmptyChecker.isAnyOneEmpty(auditId, status, changeTime)) {
            return 0;
        }
        Date date = ServiceUtils.StrToDate(changeTime);
        Audit audit = new Audit();
        audit.setAuditId(auditId);
        audit.setAuditStatus(status);
        audit.setAuditTime(date);
        if (EmptyChecker.isEmpty(auditStatusMapper.selectByPrimaryKey(status))) {
            return -2;
        }
        if (audit.equals(auditMapper.selectByPrimaryKey(auditId))) {
            return -5;
        }
        //进行状态修改
        int changeStatus = auditMapper.updateByPrimaryKeySelective(audit);
        String applicationId = auditMapper.selectByPrimaryKey(auditId).getApplicationId();
        //更新状态分类
        if (status == 1) {
            //如果申请表中状态未未审核，则将状态同步更新到申请表
            if (applicationMapper.selectByPrimaryKey(applicationId).getAuditStatus() == 0) {
                int i = applicationMapper.updateAuditStatus(applicationId, status);
                if (i < 0)
                    return i - 10;
            }
        }
        //当通过审核时，进行判断。
        else if (status == 2) {
            Integer passed = auditMapper.queryPassedCount(applicationId);
            //判断终审表终是否已经分配过该申请表
            if (EmptyChecker.isEmpty(auditEndMapper.queryAuditEndByApplicationId(applicationId))) {
                //如果该申请单有三个审核中有两个审核已经通过，则将其添加到终审表中。
                if (passed != null && passed >= 2) {
                    auditEndService.allotOneAuditEnd(applicationId);
                }
                // 同时将其申请表状态置为 ”终审中“
                applicationMapper.updateAuditStatus(applicationId, 4);
            }

        } else if (status == 3) {
            Integer notPass = auditMapper.queryNotPassCount(applicationId);
            if (notPass != null && notPass >= 2) {
                int i = applicationMapper.updateAuditStatus(applicationId, status);
                if (i < 0)
                    return i - 10;
            }
        }

        return changeStatus;
    }

    @Override
    public Page<Audit> queryAllAudit(int pageNum, int pageSize) {
        if (EmptyChecker.isAnyOneEmpty(pageNum, pageSize)) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        return auditMapper.queryAllAudit();
    }

    @Override
    public List<Audit> getAudit(String auditorId, String date) throws ParseException {
        Audit audit = new Audit();
        List<Audit> audits = new ArrayList<>();
        //判空
        if (EmptyChecker.isAnyOneEmpty(auditorId, date)) {
            audit.setAuditStatus(0);
            audits.add(audit);
            return audits;
        }
        //判断审核员是否存在
        if (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(auditorId))) {
            audit.setAuditStatus(-2);
            audits.add(audit);
            return audits;
        }
        List<Audit> returnAudits = auditMapper.queryAuditByAuditor(auditorId);
        if (EmptyChecker.notEmpty(returnAudits)) {
            //判断是否为审核中状态，不是的话 置为审核中
            for (Audit returnAudit : returnAudits) {
                if (returnAudit.getAuditStatus() == 0) {
                    changeStatus(returnAudit.getAuditId(), 1, date);
                }
            }
        }
        return returnAudits;
    }

    @Override
    public List<Audit> getAuditByApplication(String applicationId) {
        return auditMapper.getAuditByApplication(applicationId);
    }
}
