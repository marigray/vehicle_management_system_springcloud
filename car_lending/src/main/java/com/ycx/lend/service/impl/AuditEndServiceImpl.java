package com.ycx.lend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycx.lend.mapper.*;
import com.ycx.lend.pojo.Application;
import com.ycx.lend.pojo.AuditEnd;
import com.ycx.lend.pojo.Auditor;
import com.ycx.lend.service.ApplicationTypeService;
import com.ycx.lend.service.AuditEndService;
import com.ycx.lend.service.CarService;
import com.ycx.lend.service.IdService;
import com.ycx.lend.utils.EmptyChecker;
import com.ycx.lend.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @Author ycx
 * @Date 2022/1/27 16:00
 * @Description
 */
@Service
public class AuditEndServiceImpl implements AuditEndService {

    @Autowired
    AuditEndMapper auditEndMapper;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    AuditorMapper auditorMapper;
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    ApplicationTypeService applicationTypeService;
    @Autowired
    AuditStatusMapper auditStatusMapper;
    @Autowired
    CarService carService;
    @Autowired
    IdService idService;


    @Override
    public int allotOneAuditEnd(String applicationId) {
        //检查该申请单是否已被分配到终审
        if (EmptyChecker.notEmpty(auditEndMapper.queryAuditEndByApplicationId(applicationId))) {
            //重复操作
            return -5;
        }
        //分配申请单到终审员
        AuditEnd auditEnd = new AuditEnd();
        if (EmptyChecker.isEmpty(applicationId)) {
            return 0;
        }
        //判断申请编号是否存在于申请表
        if (EmptyChecker.isEmpty(applicationMapper.selectByPrimaryKey(applicationId))) {
            return -2;
        }
        auditEnd.setApplicationId(applicationId);
        //创建主键
        auditEnd.setAuditId(ServiceUtils.SetPrimaryKey(idService.getMaxId("audit_end", "audit_id")));

        HashMap<String, Object> h1 = new HashMap<>();
        h1.put("auditCount", 1000000);
        //查找没有任务的审核员 分配审核
        for (String endAuditorId : auditorMapper.queryEndAuditorId()) {
            List<AuditEnd> auditEnds = auditEndMapper.queryAuditByAuditorId(endAuditorId);
            if (EmptyChecker.isEmpty(auditEnds)) {
                h1.put("auditorId", endAuditorId);
                break;
            }
        }
        //查找任务最少的终审员 分配审核
        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
            for (HashMap<String, Object> hashMap : auditEndMapper.queryAuditOfEndAuditor()) {
                int auditCount = ServiceUtils.NumberToInt(hashMap.get("auditCount"));
                Object auditorId = hashMap.get("auditorId");
                if (auditCount < (Integer) h1.get("auditCount")) {
                    h1.put("auditorId", auditorId);
                    h1.put("auditCount", auditCount);
                }
            }
        }
        //如果没有审核员信息被存入，那么随机分配给一个终审员
        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
            while (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(h1.get("auditorId")))) {
                Random random = new Random();
                int i = Integer.parseInt(idService.getMinId("auditor", "auditor_id"));
                String auditorId = String.valueOf(random.nextInt(Integer.parseInt(idService.getMaxId("auditor", "auditor_id")) - i) + i);
                if (auditorMapper.selectByPrimaryKey(auditorId).getAuditorType() == 1) {
                    h1.put("auditorId", auditorId);
                }
            }
        }
        //过滤完成，进行赋值
        auditEnd.setAuditorId((String) h1.get("auditorId"));
        return auditEndMapper.insertSelective(auditEnd);
    }


    @Override
    public int delAuditEnd(String auditId) {
        //判空
        if (EmptyChecker.isEmpty(auditId)) {
            return 0;
        }
        //操作对象判定
        if (EmptyChecker.isEmpty(auditEndMapper.selectByPrimaryKey(auditId))) {
            return -3;
        }
        return auditEndMapper.deleteByPrimaryKey(auditId);

    }

    @Override
    public int updateAuditEnd(String auditId, String auditorId) {
        if (EmptyChecker.isAnyOneEmpty(auditId, auditorId)) {
            return 0;
        }
        AuditEnd auditEnd = new AuditEnd();
        if (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(auditorId))) {
            return -2;
        }
        //存放审核员权限，检查是否有足够权限
        Integer auditorType = auditorMapper.selectByPrimaryKey(auditorId).getAuditorType();
        if (auditorType != 1 && auditorType != 2) {
            return -6;
        }
        if (EmptyChecker.isEmpty(auditEndMapper.selectByPrimaryKey(auditId))) {
            return -3;
        }
        auditEnd.setAuditId(auditId);
        auditEnd.setAuditorId(auditorId);
        return auditEndMapper.updateByPrimaryKeySelective(auditEnd);
    }

    @Override
    public int changeStatus(String auditId, Integer status, String changeTime) throws ParseException {
        if (EmptyChecker.isAnyOneEmpty(auditId, status, changeTime)) {
            return 0;
        }
        Date date = ServiceUtils.StrToDate(changeTime);
        AuditEnd auditEnd = new AuditEnd();
        auditEnd.setAuditId(auditId);
        auditEnd.setEndStatus(status);
        auditEnd.setEndTime(date);
        if (EmptyChecker.isEmpty(auditStatusMapper.selectByPrimaryKey(status))) {
            return -2;
        }
        if (EmptyChecker.isEmpty(auditEndMapper.selectByPrimaryKey(auditId))) {
            return -3;
        }
        if (auditEnd.equals(auditEndMapper.selectByPrimaryKey(auditId))) {
            return -5;
        }
        String applicationId = auditEndMapper.selectByPrimaryKey(auditId).getApplicationId();
        Integer applicationStatus = applicationMapper.selectByPrimaryKey(applicationId).getAuditStatus();
        //判定是否为重复审核
        if (applicationStatus==2||applicationStatus==3){
            return -5;
        }
        //更新状态分类
        if (status == 3 || status == 2) {
            // 审核结果，同步更新申请表中状态
            int i = applicationMapper.updateAuditStatus(applicationId, status);
            if (i < 0)
                return i;
        }

        //审核通过，根据申请类型修改修改汽车状态
        if (status == 2) {
            //判定车辆状态是已经不是是闲置
            if (carMapper.queryCarByAuditEndId(auditId).getCarStatus() != 1) {
                //将审核状态置为不通过
                changeStatus(auditId, 3, changeTime);
                return -11;
            }
            //存放申请单信息
            Application application = applicationMapper.selectByPrimaryKey(applicationId);
            //存放对应申请表应该将车辆状态变换到的值
            Integer carStatus = applicationTypeService.getCarStatusByApply(application.getType());

            //将申请表中归还状态置为0，未归还
            int i1 = applicationMapper.updateIfReturn(applicationId, 0);
            if (i1 < 0) {
                return i1-10;
            }

            //完成修改汽车状态后将状态变化存入变化表中
            int i = carService.addCarChange(application.getCarId(), carStatus, ServiceUtils.StrToDate(changeTime));
            if (i <= 0)
                return i-10;

            //修改汽车状态
             i = carService.updateCar(application.getCarId(), carStatus);
            if (i <= 0)
                return i-10;

        }
        return auditEndMapper.updateByPrimaryKeySelective(auditEnd);
    }

    @Override
    public Page<AuditEnd> queryAllAuditEnd(int pageNum, int pageSize, String auditorId) {
        if (EmptyChecker.isAnyOneEmpty(pageNum, pageSize, auditorId)) {
            return null;
        }
        Page<AuditEnd> auditEnds = new Page<>();
        Auditor auditor = auditorMapper.selectByPrimaryKey(auditorId);
        if (EmptyChecker.notEmpty(auditor)) {
            if (auditor.getAuditorType() == 2) {
                PageHelper.startPage(pageNum, pageSize);
                return auditEndMapper.queryAllAuditEnd();
            } else {
                AuditEnd auditEnd = new AuditEnd();
                auditEnd.setEndStatus(-6);
                auditEnds.add(auditEnd);
                return auditEnds;
            }

        } else {
            AuditEnd auditEnd = new AuditEnd();
            auditEnd.setEndStatus(-2);
            auditEnds.add(auditEnd);
            return auditEnds;
        }
    }

    @Override
    public List<AuditEnd> getAuditEnd(String auditorId, String date) throws ParseException {
        AuditEnd auditEnd = new AuditEnd();
        List<AuditEnd> auditEnds = new ArrayList<>();
        //判空
        if (EmptyChecker.isAnyOneEmpty(auditorId, date)) {
            auditEnd.setEndStatus(0);
            auditEnds.add(auditEnd);
            return auditEnds;
        }
        //判断审核员是否为终审员
        Auditor auditor = auditorMapper.selectByPrimaryKey(auditorId);
        if (EmptyChecker.notEmpty(auditor)) {
            //是终审员，执行查询操作
            if (auditor.getAuditorType() == 1) {
                //存放查询结果
                List<AuditEnd> auditEndsReturn = auditEndMapper.queryAuditByAuditorId(auditorId);
                if (EmptyChecker.notEmpty(auditEndsReturn)) {
                    //判断是否为未审核，是的话置为审核中
                    for (AuditEnd returnAuditEnd : auditEndsReturn) {
                        if (returnAuditEnd.getEndStatus() == 0) {
                            changeStatus(returnAuditEnd.getAuditId(), 1, date);
                        }
                    }
                }
                return auditEndsReturn;
            } //是超级审核员，查询全部审核单
            else {
                //不是终审员，返回权限不足
                auditEnd.setEndStatus(-6);
                auditEnds.add(auditEnd);
                return auditEnds;
            }

        } else {
            auditEnd.setEndStatus(-3);
            auditEnds.add(auditEnd);
            return auditEnds;
        }
    }


}
