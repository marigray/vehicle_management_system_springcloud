package com.wang.vire.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wang.vire.mapper.*;
import com.wang.vire.pojo.RepairApply;
import com.wang.vire.service.AuditEndService;
import com.wang.vire.service.AuditService;
import com.wang.vire.service.WangService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.JsonUtils;
import com.wang.vire.utils.ServiceUtils;
import com.ycx.lend.pojo.Audit;
import com.ycx.lend.pojo.AuditEnd;
import com.ycx.lend.pojo.Auditor;
import com.ycx.lend.pojo.Status;
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
    RepairApplyMapper repairApplyMapper;
    @Autowired
    AuditEndService auditEndService;
    @Autowired
    WangService wangService;

    private final String randomNum(){
        String i = String.valueOf(Math.abs(new Random().nextInt()));
        Object auditSelByKey = JsonUtils.JsonToPojo(wangService.auditSelByKey(i), Audit.class);
        Audit auditSelByKey1 = (Audit) auditSelByKey;
        if(auditSelByKey1!=null){
            this.randomNum();
        }
        return i;
    }
    //派审核单到不同审核员，平均分配
    @Override
    public int allotAudit(String applicationId) {
        Audit audit = new Audit();
        if (EmptyChecker.isEmpty(applicationId)) {
            return 0;
        }
        //判断申请编号是否存在于申请表
        if (EmptyChecker.isEmpty(repairApplyMapper.selectByPrimaryKey(applicationId))) {
            return -2;
        }
        audit.setApplicationId(applicationId);

        //添加主键
        audit.setAuditId(this.randomNum());

        HashMap<String, Object> h1 = new HashMap<>();
        h1.put("auditCount", 1000000);
        //查找没有任务的审核员 分配审核
        Object auditorSelAllNormal = JsonUtils.JsonToListString(wangService.auditorSelAllNormal());
        List<String> auditorSelAllNormals = (List<String>) auditorSelAllNormal;
        for (String s : auditorSelAllNormals) {
            Object auditSelByAuditorId = JsonUtils.JsonToListPojo(wangService.auditSelByAuditorId(s), Audit.class);
            List<Audit> audit1 = (List<Audit>) auditSelByAuditorId;
//            List<Audit> audit1 = wangService.auditSelByAuditorId(s);
            if (EmptyChecker.isEmpty(audit1)) {
                h1.put("auditorId", s);
                break;
            }
        }
        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
            Object auditSelNumByNormalAuditor = JsonUtils.JsonToListPojo(wangService.auditSelNumByNormalAuditor(), HashMap.class);
            List<HashMap<String, Object>> auditSelNumByNormalAuditor1 = (List<HashMap<String, Object>>) auditSelNumByNormalAuditor;
            for (HashMap<String, Object> hashMap : auditSelNumByNormalAuditor1) {
                String auditorId = (String) hashMap.get("auditorId");
                //判断该审核员是否已经被分配该审核单
                Object auditSelIfAllot = JsonUtils.JsonToPojo(wangService.auditSelIfAllot(auditorId,applicationId), Audit.class);
                Audit auditSelIfAllot1 = (Audit) auditSelIfAllot;
                if (EmptyChecker.isEmpty(auditSelIfAllot1)){
                    int auditCount = ServiceUtils.NumberToInt(hashMap.get("auditCount"));
                    if (auditCount < (Integer) h1.get("auditCount")) {
                        h1.put("auditorId", auditorId);
                        h1.put("auditCount", auditCount);
                    }
                }
            }
        }
//        //如果没有审核员信息被存入，那么随机分配给一个普通审核员
//        if (EmptyChecker.isEmpty(h1.get("auditorId"))) {
//            while (EmptyChecker.isEmpty(auditorMapper.selectByPrimaryKey(h1.get("auditorId")))) {
//                Random random = new Random();
//                int i = Integer.parseInt(idService.getMinId("auditor", "auditor_id"));
//                String auditorId = String.valueOf(random.nextInt(Integer.parseInt(idService.getMaxId("auditor", "auditor_id")) - i) + i);
//                if (auditorMapper.selectByPrimaryKey(auditorId).getAuditorType() == 0) {
//                    h1.put("auditorId", auditorId);
//                }
//            }
//        }
        //过滤完成，进行赋值
        audit.setAuditorId((String) h1.get("auditorId"));
        Object o = wangService.auditInsertSelective(audit);
        int auditInsertSelective = JsonUtils.JsonToInt(o);
//        int auditInsertSelective1 = (int) auditInsertSelective;
        return auditInsertSelective;
    }

    //将审核单分配给三个不同的人
    @Override
    public int allotAllAudits() {
        int i = 1;
        int auditCount;
        for (int j = 0; j < 3; j++) {
            for (String s : repairApplyMapper.queryAllApplicationId()) {
                //判断有无值，无值时设置计数为0，防止空指针
                Object auditSelNumByAuditorId = JsonUtils.JsonToPojo(wangService.auditSelNumByAuditorId(s), HashMap.class);
                HashMap<String, Object> auditSelNumByAuditorIds = (HashMap<String, Object>) auditSelNumByAuditorId;
                if (EmptyChecker.notEmpty(auditSelNumByAuditorIds)) {
                    //防止冗余分配
                    Object auditSelAuditNumAllotAuditor = JsonUtils.JsonToPojo(wangService.auditSelAuditNumAllotAuditor(s), HashMap.class);
                    HashMap<String, Object> countMap = (HashMap<String, Object>) auditSelAuditNumAllotAuditor;
//                    HashMap<String, Object> countMap = wangService.auditSelAuditNumAllotAuditor(s);
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
        Object auditSelByKey = JsonUtils.JsonToPojo(wangService.auditSelByKey(auditId), Audit.class);
        Audit auditSelByKey1 = (Audit) auditSelByKey;
        if (EmptyChecker.isEmpty(auditSelByKey1)) {
            return -3;
        }
        Object auditDelByKey = JsonUtils.JsonToInt(wangService.auditDelByKey(auditId));
        int auditDelByKey1 = (int) auditDelByKey;
        return auditDelByKey1;
    }

    @Override
    public int updateAuditor(String auditId, String auditorId) {
        if (EmptyChecker.isAnyOneEmpty(auditId, auditorId)) {
            return 0;
        }
        Audit audit = new Audit();
        Object auditorSelByKey = JsonUtils.JsonToPojo(wangService.auditorSelByKey(auditorId), Auditor.class);
        Auditor auditorSelByKey1 = (Auditor) auditorSelByKey;
        if (EmptyChecker.isEmpty(auditorSelByKey1)) {
            return -2;
        }
        Object auditSelByKey = JsonUtils.JsonToPojo(wangService.auditSelByKey(auditId), Audit.class);
        Audit auditSelByKey1 = (Audit) auditSelByKey;
        if (EmptyChecker.isEmpty(auditSelByKey1)) {
            return -3;
        }
        audit.setAuditId(auditId);
        audit.setAuditorId(auditorId);
        Object auditUpdSelective = JsonUtils.JsonToInt(wangService.auditUpdSelective(audit));
        int auditUpdSelective1 = (int) auditUpdSelective;
        return auditUpdSelective1;
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
        //查看状态值是否存在
//        Object statusClass = JsonUtils.JsonToPojo(wangService.auditStatusSelByKey(status), Status.class);
//        Status auditStatusSelByKey=(Status)statusClass;
//        if (EmptyChecker.isEmpty(auditStatusSelByKey)) {
//            return -2;
//        }
        Object auditSelByKey = JsonUtils.JsonToPojo(wangService.auditSelByKey(auditId), Audit.class);
        Audit auditSelByKey1 = (Audit) auditSelByKey;
        if(EmptyChecker.isEmpty(auditSelByKey1)){
            return -2;
        }
        if (auditSelByKey1.getAuditStatus()==status) {
            return -5;
        }
        //进行状态修改
        Object auditUpdSelective = JsonUtils.JsonToInt(wangService.auditUpdSelective(audit));
        int changeStatus = (int) auditUpdSelective;
//        int changeStatus = wangService.auditUpdSelective(audit);
        String applicationId = auditSelByKey1.getApplicationId();
        //更新状态分类
        if (status == 1) {
            //如果申请表中状态未未审核，则将状态同步更新到申请表
            RepairApply repairApply = repairApplyMapper.selectByPrimaryKey(applicationId);
            if(EmptyChecker.isEmpty(repairApply))
                return -2;
            if (repairApply.getApplyStatus() == 0) {
                int i = repairApplyMapper.updateAuditStatus(applicationId, status);
                if (i < 0)
                    return i - 10;
            }
        }
        //当通过审核时，进行判断。
        else if (status == 2) {
            Object auditSelPassedCountByApplicationId = JsonUtils.JsonToInt(wangService.auditSelPassedCountByApplicationId(applicationId));
            Integer passed = (Integer) auditSelPassedCountByApplicationId;
//            Integer passed = wangService.auditSelPassedCountByApplicationId(applicationId);
            //判断终审表终是否已经分配过该申请表
            Object auditEndClass = JsonUtils.JsonToPojo(wangService.auditEndSelByApplicationId(applicationId), AuditEnd.class);
            AuditEnd auditEndClass1 = (AuditEnd) auditEndClass;
            if (EmptyChecker.isEmpty(auditEndClass1)) {
                //如果该申请单有三个审核中有两个审核已经通过，则将其添加到终审表中。
                if (passed != null && passed >= 2) {
                    auditEndService.allotOneAuditEnd(applicationId);
                }
                RepairApply repairApply = repairApplyMapper.selectByPrimaryKey(applicationId);
                repairApply.setApplyStatus(4);
                repairApplyMapper.updateByPrimaryKeySelective(repairApply);
                // 同时将其申请表状态置为 ”终审中“
//                repairApplyMapper.updateAuditStatus(applicationId, 4);
            }

        } else if (status == 3) {
            Object auditSelNotPassedCountByApplicationId = JsonUtils.JsonToInt(wangService.auditSelNotPassedCountByApplicationId(applicationId));
            Integer notPass = (Integer) auditSelNotPassedCountByApplicationId;
//            Integer notPass = wangService.auditSelNotPassedCountByApplicationId(applicationId);
            if (notPass != null && notPass >= 2) {
                int i = repairApplyMapper.updateAuditStatus(applicationId,3);
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
//        Page<Audit> audits=new Page<>();
//        JSON o = (JSON)wangService.auditSelAll();
//        FormatData formatData = o.toJavaObject(FormatData.class);
//        String s = JSONObject.toJSONString(formatData.getData());
//        JSONArray jsonArray=JSONArray.parseArray(s);
//        for (Object value : jsonArray) {
//            JSONObject object = (JSONObject) value;
//            audits.add(JSONObject.parseObject(object.toString(), Audit.class));
//            System.out.println(object);
//        }
        Object lasses = JsonUtils.JsonToListPojo(wangService.auditSelAll(), Audit.class);
        Page<Audit> audits = (Page<Audit>) lasses;
        return audits;
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
        Object auditorSelByKey = JsonUtils.JsonToPojo(wangService.auditorSelByKey(auditorId), Auditor.class);
        Auditor auditorSelByKey1 = (Auditor) auditorSelByKey;
        if (EmptyChecker.isEmpty(auditorSelByKey1)) {
            audit.setAuditStatus(-2);
            audits.add(audit);
            return audits;
        }
        Object auditSelByAuditorId = JsonUtils.JsonToListPojo(wangService.auditSelByAuditorId(auditorId), Audit.class);
        List<Audit> returnAudits = (List<Audit>) auditSelByAuditorId;
//        List<Audit> returnAudits = wangService.auditSelByAuditorId(auditorId);
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
        Object auditSelByApplicationId = JsonUtils.JsonToListPojo(wangService.auditSelByApplicationId(applicationId), Audit.class);
        List<Audit> auditSelByApplicationId1 = (List<Audit>) auditSelByApplicationId;
        return auditSelByApplicationId1;
    }
}
