package com.ycx.lend.web;

import com.dra.pojo.msg.FormatData;
import com.github.pagehelper.Page;
import com.ycx.lend.exception.ParamException;
import com.ycx.lend.pojo.*;
import com.ycx.lend.service.WangService;
import com.ycx.lend.utils.ServiceUtils;
import com.ycx.lend.utils.WebCheck;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/24 19:30
 * @Description
 */
@RestController
@RequestMapping("/wang")
public class WangController {
    @Autowired
    WangService wangService;

    //通过终审表ID查询终审表
    @RequestMapping("/auditEndSelByKey.do")
    public Object auditEndSelByKey(@RequestParam("auditId")String auditId){
        AuditEnd auditEnd = wangService.auditEndSelByKey(auditId);
        return new FormatData<>(auditEnd);
    }

    //通过申请单ID查询终审表
    @RequestMapping("/auditEndSelByApplicationId.do")
    public Object auditEndSelByApplicationId(@RequestParam("applicationId")String applicationId){
        AuditEnd auditEnd = wangService.auditEndSelByApplicationId(applicationId);
        return new FormatData<>(auditEnd);
    }

    //通过审核员Id查询所有该审核员的终审表
    @RequestMapping("/auditEndSelByAuditorId.do")
    public Object auditEndSelByAuditorId(@RequestParam("auditorId")String auditorId){
        List<AuditEnd> auditEnds = wangService.auditEndSelByAuditorId(auditorId);
        return new FormatData<>(auditEnds);
    }
    //查询每个终审员被分配多少申请单
    @RequestMapping("/auditEndSelNumOfEveryAuditor.do")
    public Object auditEndSelNumOfEveryAuditor(){
        List<HashMap<String, Object>> hashMaps = wangService.auditEndSelNumOfEveryAuditor();
        return new FormatData<>(hashMaps);
    }

    @RequestMapping("/queryLessNumAudit.do")
    public Object queryLessNumAudit(){
        String s = wangService.queryLessNumAudit();
        return new FormatData<>(s);
    }

    //添加终审单
    @RequestMapping("/auditEndInsertSelective.do")
    public Object auditEndInsertSelective(@RequestBody AuditEnd auditEnd){
        int i = wangService.auditEndInsertSelective(auditEnd);
        return new FormatData<>(i);
    }

    //删除终审单
    @RequestMapping("/auditEndDelByKey.do")
    public Object auditEndDelByKey(@RequestParam("auditId")String auditId){
        int i = wangService.auditEndDelByKey(auditId);
        return new FormatData<>(i);
    }
    //修改终审单
    @RequestMapping("/auditEndUpdSelective.do")
    public Object auditEndUpdSelective(@RequestBody AuditEnd auditEnd){
        int i = wangService.auditEndUpdSelective(auditEnd);
        return new FormatData<>(i);
    }
    //查询所有终审单
    @RequestMapping("/auditEndSelAll.do")
    public Object auditEndSelAll(){
        Page<AuditEnd> auditEnds = wangService.auditEndSelAll();
        return new FormatData<>(auditEnds);
    }


    //*****************
    //通过审核表ID查询审核表
    @RequestMapping("/auditSelByKey.do")
    public Object auditSelByKey(@RequestParam("auditId")String auditId){
        Audit audit = wangService.auditSelByKey(auditId);
        return new FormatData<>(audit);
    }

    //通过审核员ID查询审核表
    @RequestMapping("/auditSelByAuditorId.do")
    public Object auditSelByAuditorId(@RequestParam("auditorId")String auditorId){
        List<Audit> audits = wangService.auditSelByAuditorId(auditorId);
        return new FormatData<>(audits);
    }
    //查询每个普通审核员被分配多少申请单
    @RequestMapping("/auditSelNumByNormalAuditor.do")
    public Object auditSelNumByAuditor(){
        List<HashMap<String, Object>> hashMaps = wangService.auditSelNumByNormalAuditor();
        return new FormatData<>(hashMaps);
    }
    //查询审核单是否已被分配给该审核员
    @RequestMapping("/auditSelIfAllot.do")
    public Object auditSelIfAllot(@RequestParam("auditorId")String auditorId,@RequestParam("applicationId")String applicationId){
        Audit audit = wangService.auditSelIfAllot(auditorId, applicationId);
        return new FormatData<>(audit);
    }
    //添加审核单
    @RequestMapping("/auditInsertSelective.do")
    public Object auditInsertSelective(@RequestBody Audit audit){
        int i = wangService.auditInsertSelective(audit);
        return new FormatData<>(i);
    }
    //查询特定审核员被分配多少申请单
    @RequestMapping("/auditSelNumByAuditorId.do")
    public Object auditSelNumByAuditorId(@RequestParam("auditorId") String auditorId){
        HashMap<String, Object> stringObjectHashMap = wangService.auditSelNumByAuditorId(auditorId);
        return new FormatData<>(stringObjectHashMap);
    }
    //查询特定审核单被分配给多少审核员
    @RequestMapping("/auditSelAuditNumAllotAuditor.do")
    public Object auditSelAuditNumAllotAuditor(@RequestParam("applicationId") String applicationId){
        HashMap<String, Object> stringObjectHashMap = wangService.auditSelAuditNumAllotAuditor(applicationId);
        return new FormatData<>(stringObjectHashMap);
    }
    //删除审核单
    @RequestMapping("/auditDelByKey.do")
    public Object auditDelByKey(@RequestParam("auditId") String auditId){
        int i = wangService.auditDelByKey(auditId);
        return new FormatData<>(i);
    }
    //更改申请单
    @RequestMapping("/auditUpdSelective.do")
    public Object auditUpdSelective(@RequestBody Audit audit){
        int i = wangService.auditUpdSelective(audit);
        return new FormatData<>(i);
    }
    //查询申请单对应审核单通过的数量
    @RequestMapping("/auditSelPassedCountByApplicationId.do")
    public Object auditSelPassedCountByApplicationId(@RequestParam("applicationId") String applicationId){
        Integer integer = wangService.auditSelPassedCountByApplicationId(applicationId);
        return new FormatData<>(integer);
    }
    //查询申请单对应审核单未通过的数量
    @RequestMapping("/auditSelNotPassedCountByApplicationId.do")
    public Object auditSelNotPassedCountByApplicationId(@RequestParam("applicationId") String applicationId){
        Integer integer = wangService.auditSelNotPassedCountByApplicationId(applicationId);
        return new FormatData<>(integer);
    }
    //查询所有审核单
    @RequestMapping("/auditSelAll.do")
    public Object auditSelAll(){
        Page<Audit> audits = wangService.auditSelAll();
        return new FormatData<>(audits);
    }
    //查询指定审核员的审核单
    @RequestMapping("/auditSelByApplicationId.do")
    public Object auditSelByApplicationId(@RequestParam("applicationId") String applicationId){
        List<Audit> audits = wangService.auditSelByApplicationId(applicationId);
        return new FormatData<>(audits);
    }
    //删除特定申请单对应的审核单
    @RequestMapping("/auditDelByApplicationId.do")
    public Object auditDelByApplicationId(@RequestParam("applicationId") String applicationId){
        int i = wangService.auditDelByApplicationId(applicationId);
        return new FormatData<>(i);
    }







    //通过审核员ID查询审核员信息
    @RequestMapping("/auditorSelByKey.do")
    public Object auditorSelByKey(@RequestParam("auditorId")String auditorId){
        Auditor auditor = wangService.auditorSelByKey(auditorId);
        return new FormatData<>(auditor);
    }
    //查询所有终审员ID
    @RequestMapping("/queryEndAuditorId.do")
    public Object queryEndAuditorId(){
        List<String> strings = wangService.queryEndAuditorId();
        return new FormatData<>(strings);
    }
    //查询所有普通审核员Id
    @RequestMapping("/auditorSelAllNormal.do")
    public Object auditorSelAllNormal(){
        List<String> strings = wangService.auditorSelAllNormal();
        return new FormatData<>(strings);
    }






    //通过状态id查询状态信息(AuditStatus)
    @RequestMapping("/auditStatusSelByKey.do")
    public Object auditStatusSelByKey(Integer status){
        AuditStatus auditStatus = wangService.auditStatusSelByKey(status);
        return new FormatData<>(auditStatus);
    }



    //通过车辆id查询车辆信息
    @RequestMapping("/carSelByKey.do")
    public Object carSelByKey(@RequestParam("carId") String carId){
        Car car = wangService.carSelByKey(carId);
        return new FormatData<>(car);
    }
    //更改车辆状态
    @RequestMapping("/carUpdSelective.do")
    public Object carUpdSelective(@RequestBody Car car){
        int i = wangService.carUpdSelective(car);
        return new FormatData<>(i);
    }


    //通过状态id查询状态信息(Status)
    @RequestMapping("/statusSelByKey.do")
    public Object statusSelByKey(Integer statusNum){
        Status status = wangService.statusSelByKey(statusNum);
        return new FormatData<>(status);
    }


    //添加车辆状态变动
    @RequestMapping("/carChangeInsert.do")
    public Object carChangeInsert(@RequestBody CarChange carChange){
        int i = wangService.carChangeInsert(carChange);
        return new FormatData<>(i);
    }

    //查询特定在给定时间之前并且离给定时间最近的，申请成功的申请单
    @RequestMapping("/applicationByTime.do")
    public Object applicationByTime(@RequestParam("carId")String carId, @RequestParam("applicationTime") String applicationTime) throws ParseException {
        Application application = wangService.applicationSelByTime(carId, applicationTime);
        return new FormatData<>(application);
    }

}
