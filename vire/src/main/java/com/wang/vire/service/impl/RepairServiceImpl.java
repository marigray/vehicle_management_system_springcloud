package com.wang.vire.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wang.vire.mapper.*;
import com.wang.vire.pojo.Repair;
import com.wang.vire.service.CarService;
import com.wang.vire.service.RepairService;
import com.wang.vire.service.WangService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.JsonUtils;
import com.wang.vire.utils.ServiceUtils;
import com.ycx.lend.pojo.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @Author wang
 * @Data 2022/2/15 15:45
 * @Description
 */
@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    RepairMapper repairMapper;
//    @Autowired
//    UserMapper userMapper;
    @Autowired
    CarService carService;
    @Autowired
    RepairerMapper repairerMapper;
    @Autowired
    RepairApplyMapper repairApplyMapper;
    @Autowired
    WangService wangService;

    private final String randomNum(){
        String i = String.valueOf(Math.abs(new Random().nextInt()));
        if(repairMapper.selectByPrimaryKey(i)!=null){
            this.randomNum();
        }
        return i;
    }
    @Override
    public int addRepair(Repair repair) {

        //判空
        if (EmptyChecker.isEmpty(repair)){
            return 0;
        }else if(EmptyChecker.isAnyOneEmpty(repair.getApplyId(),repair.getRepairerId(),
                repair.getCarId(),repair.getRepairContent(),repair.getCost())){
            return 0;
        }

        /*判断用户是否存在*/
        if(EmptyChecker.isEmpty(repairerMapper.selectByPrimaryKey(repair.getRepairerId()))){
            return -2;
        }
        /*判断车辆是否存在*/
        Object carSelByKey = JsonUtils.JsonToPojo(wangService.carSelByKey(repair.getCarId()), Car.class);
        Car carSelByKey1 = (Car) carSelByKey;
        if (EmptyChecker.isEmpty(carSelByKey1)){
            return -2;
        }
        //判断申请单是否存在
        if(EmptyChecker.isEmpty(repairApplyMapper.selectByPrimaryKey(repair.getApplyId()))){
            return -2;
        }
        String repairId=this.randomNum();
        repair.setRepairId(repairId);
        repair.setCreateTime(null);
        int i = repairMapper.insertSelective(repair);
        if(i==1){
            Date date = new Date();
            try {
                carService.addCarChange(repair.getCarId(), 1, ServiceUtils.StrToDate(ServiceUtils.CstToDate(date.toString())));
            } catch (ParseException e) {
                return -4;
            }
            carService.updateCar(repair.getCarId(),1);
            return 1;
        }else{
            return -4;
        }
    }

    @Override
    public int delRepair(String repairId) {
        if(EmptyChecker.isEmpty(repairId)) return 0;
        //判断记录是否存在
        if(EmptyChecker.isEmpty(repairMapper.selectByPrimaryKey(repairId)))
            return -3;
        return repairMapper.deleteByPrimaryKey(repairId);
    }

    @Override
    public int updateRepair(Repair repair) {
        //判空
        if (EmptyChecker.isEmpty(repair)){
            return 0;
        }else if(EmptyChecker.isAnyOneEmpty(repair.getApplyId(),repair.getRepairerId(),
                repair.getCarId(),repair.getRepairContent(),repair.getCost())){
            return 0;
        }
        //判断记录是否存在
        if(EmptyChecker.isEmpty(repairMapper.selectByPrimaryKey(repair.getRepairId())))
            return -3;
        if(EmptyChecker.isEmpty(repairApplyMapper.selectByPrimaryKey(repair.getApplyId()))){
            return -2;
        }
        /*判断车辆是否存在*/
        Object carSelByKey = JsonUtils.JsonToPojo(wangService.carSelByKey(repair.getCarId()), Car.class);
        Car carSelByKey1 = (Car) carSelByKey;
        if (EmptyChecker.isEmpty(carSelByKey1)){
            return -2;
        }
        /*判断用户是否存在*/
        if(EmptyChecker.isEmpty(repairerMapper.selectByPrimaryKey(repair.getRepairerId()))){
            return -2;
        }
        Repair repair1 = new Repair();
        repair1.setRepairId(repair.getRepairId());
        repair1.setApplyId(repair.getApplyId());
        repair1.setRepairerId(repair.getRepairerId());
        repair1.setCarId(repair.getCarId());
        repair1.setRepairContent(repair.getRepairContent());
        repair1.setCost(repair.getCost());
        if(repairMapper.deleteByPrimaryKey(repair.getRepairId())!=1)
        return -4;
        return repairMapper.insertSelective(repair1);
    }

    @Override
    public Repair queryRepairById(String repairId) {
        return repairMapper.selectByPrimaryKey(repairId);
    }

    @Override
    public Page<Repair> queryRepairByApplyId(String applyId) {
        return repairMapper.queryByApplyId(applyId);
    }

    @Override
    public Page<Repair> queryRepairByCarId(String carId) {
        return repairMapper.queryByCarId(carId);
    }

    @Override
    public Page<Repair> queryRepairByUserId(String repairerId) {
        return repairMapper.queryByUserId(repairerId);
    }

    @Override
    public Page<Repair> queryAllRepair(int pageNum,int pageSize) {
        if (EmptyChecker.isAnyOneEmpty(pageNum,pageSize)) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        return repairMapper.queryAllRepair();
    }
}
