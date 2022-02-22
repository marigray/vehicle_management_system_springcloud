package com.wang.vire.service;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.Repair;

/**
 * @Author wang
 * @Data 2022/2/14 21:34
 * @Description
 */
public interface RepairService {
    //添加维修信息
    int addRepair(Repair repair);
    //删除维修信息
    int delRepair(String repairId);
    //更改维修信息
    int updateRepair(Repair repair);
    //根据ID查询维修信息
    Repair queryRepairById(String repairId);
    //根据维修申请表ID查询维修信息表
    Page<Repair> queryRepairByApplyId(String applyId);
    //根据车辆ID查询维修信息
    Page<Repair> queryRepairByCarId(String carId);
    //根据维修人ID查询维修信息
    Page<Repair> queryRepairByUserId(String repairerId);
    //查询所有维修信息
    Page<Repair> queryAllRepair(int pageNum,int pageSize);

}
