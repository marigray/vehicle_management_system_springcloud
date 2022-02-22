package com.wang.vire.service;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.Audit;

import java.text.ParseException;
import java.util.List;

/**
 * @Author wang
 * @Date 2022/1/25 15:34
 * @Description
 */
public interface AuditService {
    //派审核单到不同审核员，平均分配
    int allotAudit(String applicationId);

    //分配所有申请单,每个申请单分配给三个审核员审核
    int allotAllAudits();

    int delAudit(String auditId);

    //手动重新分配审核，修改审核表对应的审核员
    int updateAuditor(String auditId, String auditorId);

    //改变审核状态，失败和正在审核两状态将同步更新到申请表，通过时进行判断，通过的同步添加到终审表
    int changeStatus(String auditId, Integer status, String changeTime) throws ParseException;

    Page<Audit> queryAllAudit(int pageNum, int pageSize);

    //审核员查询自己的所有审核单，将状态置为 正在审核
    List<Audit> getAudit(String auditorId, String date) throws ParseException;
    //查询某个申请单的所有审核
    List<Audit> getAuditByApplication(String applicationId);
}
