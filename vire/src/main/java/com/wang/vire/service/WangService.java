package com.wang.vire.service;

import com.github.pagehelper.Page;
import com.wang.vire.utils.JsonUtils;
import com.ycx.lend.pojo.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wang
 * @Data 2022/2/24 19:39
 * @Description
 */
@FeignClient(value = "CAR-LENDING")
public interface WangService {
    @RequestMapping("/wang/auditEndSelByKey.do")
    public Object auditEndSelByKey(@RequestParam("auditId")String auditId);
    //AuditEnd

    @RequestMapping("/wang/auditEndSelByApplicationId.do")
    public Object auditEndSelByApplicationId(@RequestParam("applicationId")String applicationId);

    @RequestMapping("/wang/auditEndSelByAuditorId.do")
    public Object auditEndSelByAuditorId(@RequestParam("auditorId")String auditorId);
    //List<AuditEnd>
    @RequestMapping("/wang/auditEndSelNumOfEveryAuditor.do")
    public Object auditEndSelNumOfEveryAuditor();
    //List<HashMap<String, Object>>
    @RequestMapping("/wang/queryLessNumAudit.do")
    public Object queryLessNumAudit();
    @RequestMapping(value="/wang/auditEndInsertSelective.do",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object auditEndInsertSelective(@RequestBody AuditEnd auditEnd);

    @RequestMapping("/wang/auditEndDelByKey.do")
    public Object auditEndDelByKey(@RequestParam("auditId")String auditId);

    @RequestMapping("/wang/auditEndUpdSelective.do")
    public Object auditEndUpdSelective(@RequestBody AuditEnd auditEnd);

    @RequestMapping("/wang/auditEndSelAll.do")
    public Object auditEndSelAll();
    //Page<AuditEnd>

    @RequestMapping("/wang/auditSelByKey.do")
    public Object auditSelByKey(@RequestParam("auditId")String auditId);
    @RequestMapping("/wang/auditorSelAllNormal.do")
    public Object auditorSelAllNormal();
    @RequestMapping("/wang/auditSelByAuditorId.do")
    public Object auditSelByAuditorId(@RequestParam("auditorId")String auditorId);
    @RequestMapping("/wang/auditSelNumByNormalAuditor.do")
    public Object auditSelNumByNormalAuditor();
    @RequestMapping("/wang/auditSelIfAllot.do")
    public Object auditSelIfAllot(@RequestParam("auditorId")String auditorId,@RequestParam("applicationId")String applicationId);
    @RequestMapping(value = "/wang/auditInsertSelective.do",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object auditInsertSelective(@RequestBody Audit audit);
    @RequestMapping("/wang/auditSelNumByAuditorId.do")
    public Object auditSelNumByAuditorId(@RequestParam("auditorId") String auditorId);
    @RequestMapping("/wang/auditSelAuditNumAllotAuditor.do")
    public Object auditSelAuditNumAllotAuditor(@RequestParam("applicationId") String applicationId);
    @RequestMapping("/wang/auditDelByKey.do")
    public Object auditDelByKey(@RequestParam("auditId") String auditId);
    @RequestMapping(value = "/wang/auditUpdSelective.do",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object auditUpdSelective(@RequestBody Audit audit);
    @RequestMapping("/wang/auditSelPassedCountByApplicationId.do")
    public Object auditSelPassedCountByApplicationId(@RequestParam("applicationId") String applicationId);
    @RequestMapping("/wang/auditSelNotPassedCountByApplicationId.do")
    public Object auditSelNotPassedCountByApplicationId(@RequestParam("applicationId") String applicationId);
    @RequestMapping("/wang/auditSelAll.do")
    public Object auditSelAll();
    @RequestMapping("/wang/auditSelByApplicationId.do")
    public Object auditSelByApplicationId(@RequestParam("applicationId") String applicationId);
    @RequestMapping("/wang/auditDelByApplicationId.do")
    public Object auditDelByApplicationId(@RequestParam("applicationId") String applicationId);



    @RequestMapping("/wang/auditorSelByKey.do")
    public Object auditorSelByKey(@RequestParam("auditorId")String auditorId);

    @RequestMapping("/wang/queryEndAuditorId.do")
    public Object queryEndAuditorId();




    @RequestMapping(value = "/wang/auditStatusSelByKey.do",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object auditStatusSelByKey(Integer status);


    @RequestMapping("/wang/carSelByKey.do")
    public Object carSelByKey(@RequestParam("carId") String carId);
//    Object carClass =JsonUtils.JsonToPojo(wangService.carSelByKey(repairApply.getCarId()), Car.class);
//    Car carClass1 = (Car) carClass;
    @RequestMapping("/wang/carUpdSelective.do")
    public Object carUpdSelective(@RequestBody Car car);


    @RequestMapping(value = "/wang/statusSelByKey.do",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object statusSelByKey(Integer statusNum);

    @RequestMapping("/wang/carChangeInsert.do")
    public Object carChangeInsert(@RequestBody CarChange carChange);



    @RequestMapping("/wang/applicationByTime.do")
    public Object applicationByTime(@RequestParam("carId")String carId, @RequestParam("applicationTime") String applicationTime);


}
