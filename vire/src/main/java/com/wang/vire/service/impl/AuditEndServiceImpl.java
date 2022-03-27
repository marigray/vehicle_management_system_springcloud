package com.wang.vire.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wang.vire.mapper.*;
import com.wang.vire.pojo.RepairApply;
import com.wang.vire.service.AuditEndService;

import com.wang.vire.service.CarService;
import com.wang.vire.service.WangService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.JsonUtils;
import com.wang.vire.utils.ServiceUtils;
import com.ycx.lend.pojo.Audit;
import com.ycx.lend.pojo.AuditEnd;
import com.ycx.lend.pojo.AuditStatus;
import com.ycx.lend.pojo.Auditor;
import com.ycx.lend.pojo.Car;
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

//    @Autowired
//    AuditEndMapper auditEndMapper;
//    @Autowired
//    AuditMapper auditMapper;
//    @Autowired
//    AuditorMapper auditorMapper;
    @Autowired
    RepairApplyMapper repairApplyMapper;
//    @Autowired
//    CarMapper carMapper;
//    @Autowired
//    AuditStatusMapper auditStatusMapper;
    @Autowired
    CarService carService;
    @Autowired
    WangService wangService;



    private final String randomNum(){
        String i = String.valueOf(Math.abs(new Random().nextInt()));
        Object auditEndClass = JsonUtils.JsonToPojo(wangService.auditEndSelByKey(i), AuditEnd.class);
        AuditEnd auditEndClass1 = (AuditEnd) auditEndClass;
        if(auditEndClass1!=null){
            this.randomNum();
        }
        return i;
    }
    @Override
    public int allotOneAuditEnd(String applyId) {
        //检查该申请单是否已被分配到终审
        Object auditEndClass = JsonUtils.JsonToPojo(wangService.auditEndSelByApplicationId(applyId), AuditEnd.class);
        AuditEnd auditEndClass1 = (AuditEnd) auditEndClass;
        if (EmptyChecker.notEmpty(auditEndClass1)) {
            //重复操作
            return -5;
        }
        //分配申请单到终审员
        AuditEnd auditEnd = new AuditEnd();
        if (EmptyChecker.isEmpty(applyId)) {
            return 0;
        }
        //判断申请编号是否存在于申请表
        if (EmptyChecker.isEmpty(repairApplyMapper.selectByPrimaryKey(applyId))) {
            return -2;
        }
        auditEnd.setApplicationId(applyId);
        //创建主键
        auditEnd.setAuditId(this.randomNum());

        HashMap<String, Object> h1 = new HashMap<>();
//        h1.put("auditCount", 1000000);

        //查找没有任务的审核员 分配审核
        Object queryEndAuditorId = JsonUtils.JsonToListString(wangService.queryEndAuditorId());
        List<String> queryEndAuditorIds = (List<String>) queryEndAuditorId;
        for (String endAuditorId : queryEndAuditorIds) {
            Object auditEndSelByAuditorId = JsonUtils.JsonToListPojo(wangService.auditEndSelByAuditorId(endAuditorId), AuditEnd.class);
            List<AuditEnd> auditEndSelByAuditorIds = (List<AuditEnd>) auditEndSelByAuditorId;
//            List<AuditEnd> auditEnds = auditEndSelByAuditorIds;
            if (EmptyChecker.isEmpty(auditEndSelByAuditorIds)) {
                h1.put("auditorId", endAuditorId);
                break;
            }
        }
        //查找任务最少的终审员 分配审核
        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
//            Object auditEndSelNumOfEveryAuditor = JsonUtils.JsonToListPojo(wangService.auditEndSelNumOfEveryAuditor(), HashMap.class);
//            List<HashMap<String, Object>> auditEndSelNumOfEveryAuditors = (List<HashMap<String, Object>>) auditEndSelNumOfEveryAuditor;
//            for (HashMap<String, Object> hashMap : auditEndSelNumOfEveryAuditors) {
//                int auditCount = ServiceUtils.NumberToInt(hashMap.get("auditCount"));
//                Object auditorId = hashMap.get("auditorId");
//                if (auditCount < (Integer) h1.get("auditCount")) {
//                    h1.put("auditorId", auditorId);
//                    h1.put("auditCount", auditCount);
//                }
//            }
            Object stringClass = JsonUtils.JsonToString(wangService.queryLessNumAudit());
            String stringClass1=(String)stringClass;
            h1.put("auditorId", stringClass1);
        }
        //如果没有审核员信息被存入，那么随机分配给一个终审员
//        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
////            while (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(h1.get("auditorId")))) {
////                Random random = new Random();
////                int i = Integer.parseInt(idService.getMinId("auditor", "auditor_id"));
////                String auditorId = String.valueOf(random.nextInt(Integer.parseInt(idService.getMaxId("auditor", "auditor_id")) - i) + i);
////                if (auditorMapper.selectByPrimaryKey(auditorId).getAuditorType() == 1) {
////                    h1.put("auditorId", auditorId);
////                }
////            }
////        }
        //过滤完成，进行赋值
        auditEnd.setAuditorId((String) h1.get("auditorId"));
        Object auditEndInsertSelective = JsonUtils.JsonToInt(wangService.auditEndInsertSelective(auditEnd));
        int auditEndInsertSelective1 = (int) auditEndInsertSelective;
        return auditEndInsertSelective1;
    }


    @Override
    public int delAuditEnd(String auditId) {
        //判空
        if (EmptyChecker.isEmpty(auditId)) {
            return 0;
        }
        Object auditEndClass = JsonUtils.JsonToPojo(wangService.auditEndSelByKey(auditId), AuditEnd.class);
        AuditEnd auditEndClass1 = (AuditEnd) auditEndClass;
        //操作对象判定
        if (EmptyChecker.isEmpty(auditEndClass1)) {
            return -3;
        }
        Object auditEndDelByKey = JsonUtils.JsonToPojo(wangService.auditEndDelByKey(auditId), int.class);
        int auditEndDelByKey1 = (int) auditEndDelByKey;
        return auditEndDelByKey1;

    }

    @Override
    public int updateAuditEnd(String auditId, String auditorId) {
        if (EmptyChecker.isAnyOneEmpty(auditId, auditorId)) {
            return 0;
        }
        AuditEnd auditEnd = new AuditEnd();
        //判断审核员是否存在
        Object auditorSelByKey = JsonUtils.JsonToPojo(wangService.auditorSelByKey(auditorId), Auditor.class);
        Auditor auditorSelByKey1 = (Auditor) auditorSelByKey;
        if (EmptyChecker.isEmpty(auditorSelByKey1)) {
            return -2;
        }
        //存放审核员权限，检查是否有足够权限
        Integer auditorType = auditorSelByKey1.getAuditorType();
        if (auditorType != 1 && auditorType != 2) {
            return -6;
        }
        Object auditEndClass = JsonUtils.JsonToPojo(wangService.auditEndSelByKey(auditId), AuditEnd.class);
        AuditEnd auditEndClass1 = (AuditEnd) auditEndClass;
        if (EmptyChecker.isEmpty(auditEndClass1)) {
            return -3;
        }
        auditEnd.setAuditId(auditId);
        auditEnd.setAuditorId(auditorId);
        Object auditEndUpdSelective = JsonUtils.JsonToPojo(wangService.auditEndUpdSelective(auditEnd), int.class);
        int auditEndUpdSelective1 = (int) auditEndUpdSelective;
        return auditEndUpdSelective1;
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
        Object auditStatusSelByKey = JsonUtils.JsonToPojo(wangService.auditStatusSelByKey(status), AuditStatus.class);
        AuditStatus auditStatusSelByKey1 = (AuditStatus) auditStatusSelByKey;
        if (EmptyChecker.isEmpty(auditStatusSelByKey1)) {
            return -2;
        }
        Object auditEndClass = JsonUtils.JsonToPojo(wangService.auditEndSelByKey(auditId), AuditEnd.class);
        AuditEnd auditEndClass1 = (AuditEnd) auditEndClass;
        if (EmptyChecker.isEmpty(auditEndClass1)) {
            return -3;
        }
        if (auditEnd.equals(auditEndClass1)) {
            return -5;
        }
        String applyId = auditEndClass1.getApplicationId();
        Integer applyStatus = repairApplyMapper.selectByPrimaryKey(applyId).getApplyStatus();
        //判定是否为重复审核
        if (applyStatus==2||applyStatus==3){
            return -5;
        }
        //更新状态分类
        if (status == 3 || status == 2) {
            // 审核结果，同步更新申请表中状态
            int i = repairApplyMapper.updateAuditStatus(applyId, status);
            if (i < 0)
                return i;
        }

        //审核通过，根据申请类型修改修改汽车状态
        if (status == 2) {
            //判定车辆状态是已经不是是闲置

            Object carSelByKey =JsonUtils.JsonToPojo(wangService.carSelByKey(
                    repairApplyMapper.selectByPrimaryKey(auditEndClass1.getApplicationId()).getCarId()
            ), Car.class);
            Car carSelByKey1 = (Car) carSelByKey;
            if(carSelByKey1.getCarStatus()!=1)
            {
                //将审核状态置为不通过
                changeStatus(auditId, 3, changeTime);
                return -11;
            }
            //存放申请单信息
            RepairApply repairApply = repairApplyMapper.selectByPrimaryKey(applyId);


            //完成修改汽车状态后将状态变化存入变化表中
            int i = carService.addCarChange(repairApply.getCarId(), 5, ServiceUtils.StrToDate(changeTime));
            if (i <= 0)
                return i-10;

            //修改汽车状态
             i = carService.updateCar(repairApply.getCarId(), 5);
            if (i <= 0)
                return i-10;

        }
        Object auditEndUpdSelective = JsonUtils.JsonToPojo(wangService.auditEndUpdSelective(auditEnd), int.class);
        int auditEndUpdSelective1 = (int) auditEndUpdSelective;
        return auditEndUpdSelective1;
    }

    @Override
    public Page<AuditEnd> queryAllAuditEnd(int pageNum, int pageSize, String auditorId) {
        if (EmptyChecker.isAnyOneEmpty(pageNum, pageSize, auditorId)) {
            return null;
        }
        Page<AuditEnd> auditEnds = new Page<>();
        Object auditorSelByKey = JsonUtils.JsonToPojo(wangService.auditorSelByKey(auditorId), Auditor.class);
        Auditor auditor = (Auditor) auditorSelByKey;
//        Auditor auditor = wangService.auditorSelByKey(auditorId);
        if (EmptyChecker.notEmpty(auditor)) {
            if (auditor.getAuditorType() == 2) {
                PageHelper.startPage(pageNum, pageSize);
                Object auditEndSelAll = JsonUtils.JsonToListPojo(wangService.auditEndSelAll(), AuditEnd.class);
                Page<AuditEnd> auditEndSelAlls = (Page<AuditEnd>) auditEndSelAll;
                return auditEndSelAlls;
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
        Object auditorSelByKey = JsonUtils.JsonToPojo(wangService.auditorSelByKey(auditorId), Auditor.class);
        Auditor auditor = (Auditor) auditorSelByKey;
//        Auditor auditor = wangService.auditorSelByKey(auditorId);
        if (EmptyChecker.notEmpty(auditor)) {
            //是终审员，执行查询操作
            if (auditor.getAuditorType() == 1) {
                //存放查询结果
                Object auditEndSelByAuditorId = JsonUtils.JsonToListPojo(wangService.auditEndSelByAuditorId(auditorId), AuditEnd.class);
                List<AuditEnd> auditEndsReturn = (Page<AuditEnd>) auditEndSelByAuditorId;
//                List<AuditEnd> auditEndsReturn = wangService.auditEndSelByAuditorId(auditorId);
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
            else if(auditor.getAuditorType()==2){
                Object auditEndSelAll = JsonUtils.JsonToListPojo(wangService.auditEndSelAll(), AuditEnd.class);
                Page<AuditEnd> auditEndSelAlls = (Page<AuditEnd>) auditEndSelAll;
                return auditEndSelAlls;
            }
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
