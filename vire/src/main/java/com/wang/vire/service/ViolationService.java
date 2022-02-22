package com.wang.vire.service;

import com.github.pagehelper.Page;
import com.wang.vire.pojo.RepairApply;
import com.wang.vire.pojo.VioMessage;
import com.wang.vire.pojo.Violation;

import java.text.ParseException;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/10 11:38
 * @Description
 */
public interface ViolationService {
    //添加违章信息
    int addViolation(VioMessage vioMessage) throws ParseException;
    //删除违章信息
    int delViolation(String vioId);
    //更改违章信息状态
    int updateViolationStatus(String vioId,int vioStatus);
    //查询所有违章信息
    Page<Violation> queryAllViolation(int pageNum, int pageSize);
    //根据id查询违章信息
    Violation queryViolationById(String vioId);
    //根据用户查询违章信息
    List<Violation> queryViolationByUser(String violatorId);
}
