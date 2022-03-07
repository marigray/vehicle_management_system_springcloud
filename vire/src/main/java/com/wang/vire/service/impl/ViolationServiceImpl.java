package com.wang.vire.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wang.vire.mapper.ViolationMapper;
import com.wang.vire.pojo.VioMessage;
import com.wang.vire.pojo.Violation;
import com.wang.vire.service.ViolationService;
import com.wang.vire.service.WangService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.JsonUtils;
import com.wang.vire.utils.ServiceUtils;
import com.ycx.lend.pojo.Application;
import com.ycx.lend.pojo.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author wang
 * @Data 2022/2/15 19:23
 * @Description
 */
@Service
public class ViolationServiceImpl implements ViolationService {

    @Autowired
    ViolationMapper violationMapper;
    @Autowired
    WangService wangService;



    private final String randomNum(){
        String i = String.valueOf(Math.abs(new Random().nextInt()));
        if(violationMapper.selectByPrimaryKey(i)!=null){
            this.randomNum();
        }
        return i;
    }
    @Override
    public int addViolation(VioMessage vioMessage) throws ParseException {
        //判空
        if (EmptyChecker.isEmpty(vioMessage)) return 0;
        if(EmptyChecker.isAnyOneEmpty(vioMessage.getCarId(),vioMessage.getVioTime(),vioMessage.getVioPlace(),vioMessage.getVioAction(),
                vioMessage.getVioScore(),vioMessage.getVioCost(),vioMessage.getOrganization())){
            return 0;
        }
        //判断车辆是否存在
        Object carSelByKey = JsonUtils.JsonToPojo(wangService.carSelByKey(vioMessage.getCarId()), Car.class);
        Car carSelByKey1 = (Car) carSelByKey;
        if (EmptyChecker.isEmpty(carSelByKey1)){
            return -2;
        }
        //根据时间查找造成事故的人员
//        List<Application> applications = applicationMapper.queryApplicationByCarId(vioMessage.getCarId());
//        Date date1 = ServiceUtils.StrToDate(vioMessage.getVioTime());
//        long time=Long.parseLong(ServiceUtils.StrToDate(vioMessage.getVioTime()).toString());
//        long time1=0;
//        long time2;
//        Application app = null;
//        for (Application application : applications) {
//            if(application.getAuditStatus()==1||application.getAuditStatus()==0){
//                time2=application.getApplicationTime().getTime();
//                if(time2<=time&&time2>=time1) {
//                    time1 = time2;
//                    app = application;
//                }
//            }
//        }
//        List<Application> applications=applicationMapper.queryApplicationByTime(vioMessage.getCarId(),ServiceUtils.StrToDate(vioMessage.getVioTime()));
//        Application app= applications.get(0);
        Object applicationByTime = JsonUtils.JsonToPojo(wangService.applicationByTime(vioMessage.getCarId(), vioMessage.getVioTime()), Application.class);
        Application app = (Application) applicationByTime;
//        Application app = wangService.applicationByTime(vioMessage.getCarId(), ServiceUtils.StrToDate(vioMessage.getVioTime()));
        Violation violation = new Violation();
        //判断用户是否存在,如果不存在，将其设为null
        if(EmptyChecker.isEmpty(app)){
            violation.setViolatorId(null);
        }
//        else if(EmptyChecker.isEmpty(userMapper.selectByPrimaryKey(app.getUserId()))){
//            return -2;
//        }
        else{
            violation.setViolatorId(app.getUserId());
        }
        violation.setVioId(this.randomNum());
        violation.setCarId(vioMessage.getCarId());
        Date date = ServiceUtils.StrToDate(vioMessage.getVioTime());
        violation.setVioTime(date);
        violation.setVioPlace(vioMessage.getVioPlace());
        violation.setVioAction(vioMessage.getVioAction());
        violation.setVioScore(vioMessage.getVioScore());
        violation.setVioCost(vioMessage.getVioCost());
        violation.setOrganization(vioMessage.getOrganization());

        return violationMapper.insertSelective(violation);
    }

    @Override
    public int delViolation(String vioId) {
        if(EmptyChecker.isEmpty(vioId)){
            return 0;
        }
        if(EmptyChecker.isEmpty(violationMapper.selectByPrimaryKey(vioId))){
            return -3;
        }
        return violationMapper.deleteByPrimaryKey(vioId);
    }

    @Override
    public int updateViolationStatus(String vioId, int vioStatus) {
        if(EmptyChecker.isAnyOneEmpty(vioId,vioStatus)){
            return 0;
        }
        if(EmptyChecker.isEmpty(violationMapper.selectByPrimaryKey(vioId))){
            return -3;
        }
        if(vioStatus!=1&&vioStatus!=0){
            return -4;
        }

        Violation violation = violationMapper.selectByPrimaryKey(vioId);
        if(vioStatus==violation.getVioStatus()){
            return -5;
        }
        violation.setVioStatus(vioStatus);
        return violationMapper.updateByPrimaryKeySelective(violation);
    }

    @Override
    public Page<Violation> queryAllViolation(int pageNum, int pageSize) {
        if (EmptyChecker.isAnyOneEmpty(pageNum,pageSize)) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        return violationMapper.queryAllViolation();
    }

    @Override
    public Violation queryViolationById(String vioId) {
        return violationMapper.selectByPrimaryKey(vioId);
    }

    @Override
    public List<Violation> queryViolationByUser(String violatorId) {
        return violationMapper.queryByUserId(violatorId);
    }


}
