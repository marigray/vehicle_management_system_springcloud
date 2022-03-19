package com.ycx.lend.web;

import com.dra.pojo.msg.FormatData;
import com.github.pagehelper.Page;
import com.ycx.lend.exception.ParamException;
import com.ycx.lend.pojo.Application;
import com.ycx.lend.service.ApplicationService;
import com.ycx.lend.service.CarService;
import com.ycx.lend.utils.EmptyChecker;
import com.ycx.lend.utils.WebCheck;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/21 21:26
 * @Description 申请表管理
 */
@RestController
@RequestMapping("/apply")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    CarService carService;

    /**
     * 添加
     *
     * @param application 申请单对象
     * @return 成功返回success，失败抛出异常
     * @description 递交申请表，添加到表中
     */
    @RequestMapping("/add.do")
    public FormatData<Object> addApplication(@RequestBody Application application) throws ParamException {
        int i = applicationService.addApplication(application);
        if (i > 1) {
            String s = carService.queryCarStatusName(i);
            WebCheck.isError(s);
            throw new ParamException("申请的车辆" + s, 409);
        }
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 删除
     *
     * @param applicationId 申请单号
     * @return 成功信息
     * @description 删除对应申请单
     */
    @RequestMapping("/del.do")
    public FormatData<Object> delApplication(String applicationId) throws ParamException {
        int i = applicationService.delApplication(applicationId);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 修改
     *
     * @param application 申请单对象
     * @return 成功信息
     * @description 修改申请单
     */
    @RequestMapping("/update.do")
    public FormatData<Object> updateApplication(@RequestBody Application application) throws ParamException {
        int i = applicationService.updateApplication(application);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    /**
     * 查询
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return 全部申请表信息
     */
    @RequestMapping("/queryAll.do")
    public FormatData<Object> queryAllApplication(@RequestParam("pageNum") int pageNum,
                                                  @RequestParam("pageSize") int pageSize) throws ParamException {
        Page<Application> applications = applicationService.queryAllApplication(pageNum, pageSize);
        WebCheck.isError(applications);
        return new FormatData<>(applications);
    }

    /**
     * 按id查询对应申请表
     *
     * @param applicationId 申请表单号
     * @return 对应申请表信息
     */
    @RequestMapping("/queryById.do")
    public FormatData<Object> queryApplicationById(String applicationId) throws ParamException {
        if (EmptyChecker.isEmpty(applicationId)) {
            WebCheck.isError(0);
        }
        Application application = applicationService.queryApplicationById(applicationId);
        return new FormatData<>(application);
    }

    /**
     * 按用户id查询用户的申请表
     *
     * @param userId 用户id
     * @return 对应用户下所有的申请表
     */
    @RequestMapping("/queryByUser.do")
    public FormatData<Object> queryApplicationByUser(String userId) throws ParamException {
        if (EmptyChecker.isEmpty(userId)) {
            WebCheck.isError(0);
        }
        List<Application> applications = applicationService.queryApplicationByUser(userId);
        return new FormatData<>(applications);
    }

    @RequestMapping("/queryByUserAndCar")
    public FormatData<Object> queryByUserAndCar(@RequestParam("userId") String userId,
                                                @RequestParam("carId") String carId) throws ParamException {
        if (EmptyChecker.isAnyOneEmpty(userId,carId)){
            WebCheck.isError(0);
        }
        Application application = applicationService.queryByUserAndCar(userId, carId);
        return new FormatData<>(application);
    }

    /**查询
     *
     * @return 所有未归还的申请
     */
    @RequestMapping("/queryNotReturn.do")
    public FormatData<Object> queryNotReturn() {
        List<Application> applications = applicationService.queryNotReturnApplication();
        return new FormatData<>(applications);
    }
}
