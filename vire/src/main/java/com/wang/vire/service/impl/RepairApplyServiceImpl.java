package com.wang.vire.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.wang.vire.mapper.*;
import com.wang.vire.pojo.RepairApply;
import com.wang.vire.service.AuditService;
import com.wang.vire.service.RepairApplyService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.RandomNum;
import com.wang.vire.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @Author wang
 * @Data 2022/1/23 21:38
 * @Description
 */
@Service
public class RepairApplyServiceImpl implements RepairApplyService {

    @Autowired
    RepairApplyMapper repairApplyMapper;
    @Autowired
    AuditStatusMapper auditStatusMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    AuditService auditService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RepairMapper repairMapper;
    @Autowired
    AuditEndMapper auditEndMapper;


    private final String randomNum(){
        String i = String.valueOf(Math.abs(new Random().nextInt()));
        if(repairApplyMapper.selectByPrimaryKey(i)!=null){
            this.randomNum();
        }
        return i;
    }
    //添加申请
    @Override
    public int addRepairApply(RepairApply repairApply) {
        /*判空*/
        if (EmptyChecker.isEmpty(repairApply)){
            return 0;
        }else if(EmptyChecker.isAnyOneEmpty(repairApply.getApplicantId(),repairApply.getCarId(),
                repairApply.getRepairReasons()
                )){
            return 0;
        }
        /*判断用户是否存在*/
        if(EmptyChecker.isEmpty(userMapper.selectByPrimaryKey(repairApply.getApplicantId()))){
            return -2;
        }
        /*判断车辆是否存在*/
        if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(repairApply.getCarId()))){
            return -2;
        }
        /*
        判断是否有该车辆的未审核和审核中状态存在
         */
        if (EmptyChecker.notEmpty(repairApplyMapper.queryApplyByCarIdAndApplyStatus(repairApply.getCarId(),0)
                )||EmptyChecker.notEmpty(repairApplyMapper.queryApplyByCarIdAndApplyStatus(repairApply.getCarId(),1))
        ) {
            return -1;
        }
        /*
        生成主键，并设置时间为空，初始状态为未审核
         */
        repairApply.setApplyId(this.randomNum());
        repairApply.setApplyTime(null);
        repairApply.setApplyStatus(0);
        //return repairApplyMapper.insertSelective(repairApply);
        int insert=repairApplyMapper.insertSelective(repairApply);
        //添加申请单完毕后将其同步分配到审核表中
        String applyId = repairApply.getApplyId();
        int i = 1;
        int auditCount;
        //每个申请分配给三个不同审核员审核
        for (int j = 0; j < 3; j++) {
            //防止冗余分配
            HashMap<String, Object> countMap = auditMapper.queryAuditorCountById(applyId);
            //判断有无值，无值时设置计数为0，防止空指针
            if (EmptyChecker.notEmpty(countMap)) {
                auditCount = ServiceUtils.NumberToInt(countMap.get("applicationCount"));
            } else auditCount = 0;
            if (auditCount < 3)
                i = auditService.allotAudit(applyId);
            if (i <= 0) {
                return i;
            }
        }
        return insert;
    }

    //删除申请
    @Override
    public int delRepairApply(String applyId) {
        if (EmptyChecker.isEmpty(applyId)){
            return 0;
        }else if(EmptyChecker.isEmpty(repairApplyMapper.selectByPrimaryKey(applyId))){
            return -3;
        }
        //判断该申请是否已通过开始维修
        if(EmptyChecker.notEmpty(repairMapper.queryByApplyId(applyId))){
            return -7;
        }
        //判断该申请表是否已经通过终审
        if(EmptyChecker.notEmpty(auditEndMapper.queryByApplicationId(applyId))&&
                auditEndMapper.queryStatusByApplicationId(applyId)==2){
            return -7;
        }
        //删除终审表中的信息
        if (EmptyChecker.notEmpty(auditEndMapper.queryByApplicationId(applyId))){
            auditEndMapper.delByApplicationId(applyId);
        }
        //删除审核表中的信息
        auditMapper.deleteByApplicationId(applyId);
        return repairApplyMapper.deleteByPrimaryKey(applyId);
    }

    //修改申请
    @Override
    public int updateRepairApply(RepairApply repairApply) {
        if (EmptyChecker.isEmpty(repairApply)){
            return 0;
        }else if(EmptyChecker.isAnyOneEmpty(repairApply.getApplicantId(),repairApply.getCarId(),
                repairApply.getRepairReasons())){
            return 0;
        }
        /*判断用户是否存在*/
        if(EmptyChecker.isEmpty(userMapper.selectByPrimaryKey(repairApply.getApplicantId()))){
            return -2;
        }
        if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(repairApply.getCarId()))){
            return -2;
        }
        //判断是否存在该申请单
        if (EmptyChecker.isEmpty(repairApplyMapper.selectByPrimaryKey(repairApply.getApplyId()))){
            return -3;
        }
        //判断申请单是否是未审核
        if(repairApplyMapper.selectByPrimaryKey(repairApply.getApplyId()).getApplyStatus()!=0&&
                repairApplyMapper.selectByPrimaryKey(repairApply.getApplyId()).getApplyStatus()!=3){
            return -6;
        }
//        RepairApply repairApply1 = new RepairApply();
//        repairApply1.setApplyId(repairApply.getApplyId());
//        repairApply1.setApplicantId(repairApply.getApplyId());
//        repairApply1.setCarId(repairApply.getCarId());
//        repairApply1.setRepairReasons(repairApply.getRepairReasons());
//        repairApplyMapper.deleteByPrimaryKey(repairApply.getApplyId());
//        return repairApplyMapper.insertSelective(repairApply1);
        return repairApplyMapper.updateByPrimaryKeySelective(repairApply);
    }

    //查询所有申请
    @Override
    public Page<RepairApply> queryAllRepairApply(int pageNum, int pageSize) {
        if (EmptyChecker.isAnyOneEmpty(pageNum,pageSize)) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        return repairApplyMapper.queryAllApply();
    }

    //通过ID查询申请
    @Override
    public RepairApply queryRepairApplyById(String repairId) {
        return repairApplyMapper.selectByPrimaryKey(repairId);
    }

    //通过申请人ID查询申请
    @Override
    public List<RepairApply> queryRepairApplyByUser(String applicantId) {
        return repairApplyMapper.queryApplyByUser(applicantId);
    }
}
