package com.wang.vire.web;

import com.github.pagehelper.Page;
import com.wang.vire.exception.ParamException;
import com.wang.vire.pojo.FormatData;
import com.wang.vire.pojo.Repair;
import com.wang.vire.pojo.VioMessage;
import com.wang.vire.pojo.Violation;
import com.wang.vire.service.ViolationService;
import com.wang.vire.utils.WebCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/15 21:24
 * @Description
 */
@RestController
@RequestMapping("/vio")
public class violationController {
    @Autowired
    ViolationService violationService;

    /**
     * 添加违章信息
     * @param vioMessage 违章信息
     * @return
     * @throws ParseException
     * @throws ParamException
     */
    @RequestMapping("/add.do")
    public FormatData<Object> addViolation(@RequestBody(required = false)VioMessage vioMessage) throws ParseException, ParamException {
        int i=violationService.addViolation(vioMessage);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 根据ID删除违章信息
     * @param vioId 违章信息表ID
     * @return 是否成功信息
     * @throws ParamException
     */
    @RequestMapping("/del.do")
    public FormatData<Object> delViolation(String vioId)throws ParamException {
        int i = violationService.delViolation(vioId);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 更改违章信息状态
     * @param vioId 违章信息表ID
     * @param vioStatus 违章信息表状态
     * @return 是否成功信息
     * @throws ParamException
     */
    @RequestMapping("/update.do")
    public FormatData<Object> updateViolationStatus(String vioId,int vioStatus)throws ParamException{
        int i=violationService.updateViolationStatus(vioId,vioStatus);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 查询全部违章信息表
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @return 全部违章信息表
     * @throws ParamException
     */
    @RequestMapping("/queryAll.do")
    public FormatData<Object> queryAllViolation(@RequestParam("pageNum") int pageNum,
                                                  @RequestParam("pageSize")int pageSize) throws ParamException {
        Page<Violation> violation = violationService.queryAllViolation(pageNum,pageSize);
        //System.out.println(repairApplies);
        return new FormatData<>(violation);
    }

    /**
     * 根据ID查询维修信息表
     * @param vioId 违章信息表ID
     * @return id对应违章信息表
     */
    @RequestMapping("/queryById.do")
    public FormatData<Object> queryViolationById(String vioId) {
        Violation violation = violationService.queryViolationById(vioId);
        return new FormatData<>(violation);
    }

    /**
     * 根据用户查询违章信息表
     * @param violatorId 违章人ID
     * @return 违章人的所有违章信息
     */
    @RequestMapping("/queryByUser.do")
    public FormatData<Object> queryViolationByUser(String violatorId) {
        List<Violation> violations = violationService.queryViolationByUser(violatorId);
        return new FormatData<>(violations);
    }
}

