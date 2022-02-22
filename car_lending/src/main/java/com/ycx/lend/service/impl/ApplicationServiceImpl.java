package com.ycx.lend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycx.lend.mapper.*;
import com.ycx.lend.pojo.Application;
import com.ycx.lend.pojo.Car;
import com.ycx.lend.service.ApplicationService;
import com.ycx.lend.service.AuditEndService;
import com.ycx.lend.service.AuditService;
import com.ycx.lend.service.IdService;
import com.ycx.lend.utils.EmptyChecker;
import com.ycx.lend.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 申请表管理
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    ApplicationTypeMapper applicationTypeMapper;
    @Autowired
    AuditStatusMapper auditStatusMapper;
    @Autowired
    AuditEndService auditEndService;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    AuditService auditService;
    @Autowired
    AuditEndMapper auditEndMapper;
    @Autowired
    IdService idService;

    //添加申请
    @Override
    public int addApplication(Application application) {
        /*判空*/
        if (EmptyChecker.isEmpty(application)) {
            return 0;
        }
        //判定当是借车和还车申请时需要车牌号，是派车申请时不需要车牌号。用户id和类型编号不能为空
        else if (EmptyChecker.isAnyOneEmpty(application.getUserId(), application.getType()) ||
                (application.getType() != 1 && EmptyChecker.isEmpty(application.getCarId()))) {
            return 0;
        }
        /*生成主键*/
        application.setApplicationId(ServiceUtils.SetPrimaryKey(idService.getMaxId("application", "application_id")));

        /*判断申请类型是否存在于关联表中*/
        if (EmptyChecker.isEmpty(applicationTypeMapper.selectByPrimaryKey(application.getType()))) {
            return -2;
        }
        /*------------------------------------------------------------------------------------------------*/
        int insert = -4;
        //执行后续对审核表的操作以及申请表的插入过滤
        switch (application.getType()) {
            //借车申请,检查车辆空闲状态
            case 0:
                //过滤不存在的车牌号
                if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(application.getCarId()))) {
                    return -2;
                }
                //存放申请的车辆状态
                Car car = carMapper.selectByPrimaryKey(application.getCarId());
                Integer carStatus = car.getCarStatus();
                //车辆闲置，执行申请表插入操作，并进行审核分配。
                if (carStatus == 1) {
                    insert = applicationMapper.insertSelective(application);
                    int i = allotApplication(application.getApplicationId());
                    if (i <= 0)
                        return i - 10;
                } else return car.getCarStatus();
                break;

            //派车申请，随机分配空闲车辆
            case 1:
                List<Car> cars = carMapper.queryIdleCar();
                Random random = new Random();
                int i = random.nextInt(cars.size());
                application.setCarId(cars.get(i).getCarId());
                //分配完成，执行申请表插入操作，并进行审核分配。
                insert = applicationMapper.insertSelective(application);
                int j = allotApplication(application.getApplicationId());
                if (j <= 0)
                    return j - 10;
                break;
        }

        return insert;
    }

    @Override
    public int allotApplication(String applicationId) {
        //添加申请单完毕后将其同步分配到审核表中（借车，派车申请时执行）
        int i = 1;
        int auditCount;

        //如果是还车申请，直接进入终审
        if (applicationMapper.selectByPrimaryKey(applicationId).getType() == 2) {
            return auditEndService.allotOneAuditEnd(applicationId);
        }

        //每个申请分配给三个不同审核员审核
        for (int j = 0; j < 3; j++) {
            //防止冗余分配
            HashMap<String, Object> countMap = auditMapper.queryAuditorCountById(applicationId);
            //判断有无值，无值时设置计数为0，防止空指针
            if (EmptyChecker.notEmpty(countMap)) {
                auditCount = ServiceUtils.NumberToInt(countMap.get("applicationCount"));
            } else auditCount = 0;
            if (auditCount < 3)
                i = auditService.allotAudit(applicationId);
        }
        return i;
    }

    //删除
    @Override
    public int delApplication(String applicationId) {
        if (EmptyChecker.isEmpty(applicationId)) {
            return 0;
        } else if (EmptyChecker.isEmpty(applicationMapper.selectByPrimaryKey(applicationId))) {
            return -3;
        }
        //判断该申请是否已通过但未归还车辆
        if (applicationMapper.selectByPrimaryKey(applicationId).getIfReturn() == 0) {
            return -7;
        }
        //删除审核表中数据
        int i = auditMapper.delByApplicationId(applicationId);
        if (i <= 0) {
            return i;
        }
        //如果已近终审，删除终审表中内容
        if (EmptyChecker.notEmpty(auditEndMapper.queryAuditEndByApplicationId(applicationId))) {
            int i1 = auditEndMapper.delByApplicationId(applicationId);
            if (i1 <= 0) {
                return i1 - 10;
            }
        }
        return applicationMapper.deleteByPrimaryKey(applicationId);
    }


    @Override
    public int updateApplication(Application application) {
        if (EmptyChecker.isEmpty(application)) {
            return 0;
        } else if (EmptyChecker.isEmpty(application.getApplicationId())) {
            return 0;
        } else if (EmptyChecker.isEmpty(applicationMapper.selectByPrimaryKey(application.getApplicationId()))) {
            return -3;
        } else if (EmptyChecker.isEmpty(applicationTypeMapper.selectByPrimaryKey(application.getType()))) {
            return -2;
        } else if (EmptyChecker.notEmpty(application.getAuditStatus())) {
            if (EmptyChecker.isEmpty(auditStatusMapper.selectByPrimaryKey(application.getAuditStatus()))) {
                return -2;
            }
        }
        if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(application.getCarId()))) {
            return -2;
        }
        //存放未修改前申请单
        Application application1 = applicationMapper.selectByPrimaryKey(application.getApplicationId());
        //如果申请已经开始审核，不允许修改
        if (application1.getAuditStatus() != 0||!application.getUserId().equals(application1.getUserId())) {
            return -7;
        }
        return applicationMapper.updateByPrimaryKeySelective(application);
    }

    @Override
    public Page<Application> queryAllApplication(int pageNum, int pageSize) {
        if (EmptyChecker.isAnyOneEmpty(pageNum, pageSize)) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        return applicationMapper.queryAllApplication();
    }

    @Override
    public Application queryApplicationById(String applicationId) {
        return applicationMapper.selectByPrimaryKey(applicationId);
    }

    @Override
    public List<Application> queryApplicationByUser(String userId) {
        return applicationMapper.queryApplicationByUser(userId);
    }

    @Override
    public List<Application> queryNotReturnApplication() {
        return applicationMapper.queryNotReturnApplication();
    }
}
