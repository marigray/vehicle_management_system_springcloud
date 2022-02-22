package com.wang.vire.service;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.RepairApply;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/1/23 21:29
 * @Description
 */
public interface RepairApplyService {
    int addRepairApply(RepairApply repairApply);

    int delRepairApply(String applyId);

    int updateRepairApply(RepairApply repairApply);

    Page<RepairApply> queryAllRepairApply(int pageNum,int pageSize);

    RepairApply queryRepairApplyById(String repairId);

    List<RepairApply> queryRepairApplyByUser(String applicantId);

}
