package com.wang.vire.web;

import com.dra.pojo.msg.FormatData;
import com.github.pagehelper.Page;
import com.wang.vire.exception.ParamException;
import com.wang.vire.service.AuditService;
import com.wang.vire.utils.WebCheck;
import com.ycx.lend.pojo.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/8 10:05
 * @Description
 */
@RestController
@RequestMapping("/repairAudit")
public class AuditController {
    @Autowired
    AuditService auditService;


    /**
     * 删除审核单
     * @param auditId 审核单号
     * @return 是否成功信息
     */
    @RequestMapping("/del.do")
    public FormatData<Object> delAudit(@RequestParam(value = "auditId",required = false) String auditId) throws ParamException {
        int i = auditService.delAudit(auditId);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }


    /**
     * 审核 修改审核单审核状态，同步更新申请表中审核状态
     * @param auditId 申请单号
     * @param status 状态码
     * @param changeTime 当前时间
     * @return 是否成功信息
     */
    @RequestMapping("/changeStatus.do")
    public FormatData<Object> changeStatus(@RequestParam(value = "auditId",required = false) String auditId,
                                           @RequestParam(value = "status",required = false) Integer status,
                                           @RequestParam(value = "changeTime",required = false) String changeTime) throws ParamException, ParseException {
        int i = auditService.changeStatus(auditId, status, changeTime);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }


    /**
     * 查询全部审核表
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @return 全部审核表
     */
    @RequestMapping("/queryAll.do")
    public FormatData<Object> queryAllAudit(@RequestParam(value = "pageNum",required = false) int pageNum,
                                            @RequestParam(value = "pageSize",required = false) int pageSize) throws ParamException {
        Page<Audit> audits = auditService.queryAllAudit(pageNum, pageSize);
        WebCheck.isError(audits);
        return new FormatData<>(audits);
    }

    /**
     * 审核员查询自己的所有审核单，将状态置为 正在审核
     * @param auditorId 审核员编号
     * @param date 当前时间
     * @return 审核员对应的审核表
     */
    @RequestMapping("/queryByUser.do")
    public FormatData<Object> getAudit(@RequestParam(value = "auditorId",required = false) String auditorId,
                                       @RequestParam(value = "date",required = false) String date) throws ParamException, ParseException {
        List<Audit> audit = auditService.getAudit(auditorId, date);
        Integer auditStatus = audit.get(0).getAuditStatus();
        if (audit.get(0).getAuditId() == null) {
            WebCheck.isError(auditStatus);
        }
        return new FormatData<>(audit);
    }


    /**
     * 查询某个申请单对应的所有审核单
     * @param applicationId 申请单号
     * @return 申请单对应的所有审核单
     */
    @RequestMapping("/queryByApplication.do")
    public FormatData<Object> queryByApplication(@RequestParam(value = "applicationId",required = false) String applicationId) throws ParamException {
        if (applicationId==null){
            WebCheck.isError(0);
        }
        List<Audit> auditByApplication = auditService.getAuditByApplication(applicationId);
        return new FormatData<>(auditByApplication);
    }



}
