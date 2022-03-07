package com.wang.vire.service;

import com.ycx.lend.pojo.AuditEnd;

import java.text.ParseException;
import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/27 15:33
 * @Description
 */
public interface AuditEndService {
    //将通过审核的审核单插入终审表，分配单个
    int allotOneAuditEnd(String applicationId);

    int delAuditEnd(String auditId);

    int updateAuditEnd(String auditId, String auditorId);

    //修改终审状态，所有状态改变同步更新到申请表
    int changeStatus(String auditId, Integer status, String changeTime) throws ParseException;

    List<AuditEnd> queryAllAuditEnd(int pageNum, int pageSize, String auditorId);

    //终审员开始审核
    List<AuditEnd> getAuditEnd(String auditorId, String date) throws ParseException;

}
