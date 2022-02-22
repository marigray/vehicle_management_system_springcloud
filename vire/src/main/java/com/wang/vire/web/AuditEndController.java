package com.wang.vire.web;

import com.wang.vire.exception.ParamException;
import com.wang.vire.pojo.AuditEnd;
import com.wang.vire.pojo.FormatData;
import com.wang.vire.service.AuditEndService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.WebCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/12 10:21
 * @Description
 */
@RestController
@RequestMapping("/repairAuditEnd")
public class AuditEndController {


    @Autowired
    AuditEndService auditEndService;

    /**
     * 删除终审单
     * @param auditId 终审单ID
     * @return 是否成功信息
     */
    @RequestMapping("/del.do")
    public FormatData<Object> delAudit(@RequestParam(value = "auditId",required = false) String auditId) throws ParamException {
        int i = auditEndService.delAuditEnd(auditId);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }


    /**
     * 修改审核单审核状态，同步更新申请表中审核状态
     * @param auditId 申请单号
     * @param status 状态码
     * @param changeTime 当前时间
     * @return 是否成功信息
     */
    @RequestMapping("/changeStatus.do")
    public FormatData<Object> changeStatus(@RequestParam(value = "auditId",required = false) String auditId,
                                           @RequestParam(value = "status",required = false) Integer status,
                                           @RequestParam(value = "changeTime",required = false) String changeTime) throws ParamException, ParseException {
        int i = auditEndService.changeStatus(auditId, status, changeTime);
        if (i==-11)
            throw new ParamException("车辆不是闲置状态", 441);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }


    /**
     * 查询全部审核表
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @param auditorId 审核员ID
     * @return 全部审核表
     */
    @RequestMapping("/queryAll.do")
    public FormatData<Object> queryAllAudit(@RequestParam(value = "pageNum",required = false) int pageNum,
                                            @RequestParam(value = "pageSize",required = false) int pageSize,
                                            @RequestParam(value = "auditorId",required = false) String auditorId) throws ParamException {
        List<AuditEnd> auditEnds = auditEndService.queryAllAuditEnd(pageNum, pageSize,auditorId);
        if (EmptyChecker.notEmpty(auditEnds)) {
            Integer auditStatus = auditEnds.get(0).getEndStatus();
            if (auditEnds.get(0).getAuditId() == null) {
                WebCheck.isError(auditStatus);
            }
        }
        return new FormatData<>(auditEnds);
    }

    /**
     * 审核员查询自己的所有审核单，将状态置为 正在审核
     * @param auditorId 审核员编号
     * @param date 当前时间
     * @return 审核员对应的审核表
     */
    @RequestMapping("/queryByUser.do")
    public FormatData<Object> queryByUser(@RequestParam(value = "auditorId",required = false) String auditorId,
                                       @RequestParam(value = "date",required = false) String date) throws ParamException, ParseException {
        List<AuditEnd> auditEnd = auditEndService.getAuditEnd(auditorId, date);
        if (EmptyChecker.notEmpty(auditEnd)) {
            Integer auditStatus = auditEnd.get(0).getEndStatus();
            if (auditEnd.get(0).getAuditId() == null) {
                WebCheck.isError(auditStatus);
            }
        }
        return new FormatData<>(auditEnd);
    }

}
