package com.wang.vire.web;

import com.github.pagehelper.Page;
import com.wang.vire.exception.ParamException;
import com.wang.vire.pojo.FormatData;
import com.wang.vire.pojo.RepairApply;
import com.wang.vire.service.RepairApplyService;
import com.wang.vire.utils.WebCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author wang
 * @Data 2022/1/26 15:13
 * @Description 申请表管理
 */

@RestController
@RequestMapping("/repairApply")
public class RepairApplyController {
    @Autowired
    RepairApplyService repairApplyService;

    /**
     * 添加维修申请
     * @param repairApply 维修申请表对象
     * @return 是否成功信息
     */
    @RequestMapping("/add.do")
    public FormatData<Object> addRepairApply(@RequestBody(required=false) RepairApply repairApply)throws ParamException{
        int i=repairApplyService.addRepairApply(repairApply);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 删除维修申请
     * @param applyId 维修申请表ID
     * @return 是否成功信息
     */
    @RequestMapping("/del.do")
    public FormatData<Object> delRepairApply(String applyId)throws ParamException{
        int i=repairApplyService.delRepairApply(applyId);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 更改维修申请
     * @param repairApply 维修申请表对象
     * @return 是否成功信息
     */
    @RequestMapping("/update.do")
    public FormatData<Object> updateRepairApply(@RequestBody(required=false) RepairApply repairApply)throws ParamException{
        int i=repairApplyService.updateRepairApply(repairApply);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 查询全部申请表信息
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @return 全部申请表信息
     */
    @RequestMapping("/queryAll.do")
    public FormatData<Object> queryAllApplication(@RequestParam(value = "pageNum",required = false) int pageNum,
                                                  @RequestParam(value = "pageSize",required = false)int pageSize) throws ParamException {
        Page<RepairApply> repairApplies = repairApplyService.queryAllRepairApply(pageNum,pageSize);
        //System.out.println(repairApplies);
        return new FormatData<>(repairApplies);
    }

    /**
     *按id查询对应申请表
     * @param applyId 维修申请表ID
     * @return 申请表信息
     */
    @RequestMapping("/queryById.do")
    public FormatData<Object> queryRepairApplyById(String applyId) throws ParamException {
        RepairApply repairApply = repairApplyService.queryRepairApplyById(applyId);
        return new FormatData<>(repairApply);
    }

    /**
     *按用户id查询用户的申请表
     * @param applicantId 申请人ID
     * @return 对应用户下所有的申请表
     */
    @RequestMapping("/queryByUser.do")
    public FormatData<Object> queryRepairApplyByUser(String applicantId) throws ParamException {
        List<RepairApply> repairApplies = repairApplyService.queryRepairApplyByUser(applicantId);
        return new FormatData<>(repairApplies);
    }
}
