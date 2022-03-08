package com.ycx.lend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dra.pojo.msg.FormatData;
import com.ycx.lend.exception.ParamException;
import com.ycx.lend.mapper.*;
import com.ycx.lend.pojo.Application;
import com.ycx.lend.pojo.Car;
import com.ycx.lend.pojo.CarChange;
import com.ycx.lend.pojo.CarGps;
import com.ycx.lend.service.CarService;
import com.ycx.lend.service.CompanyService;
import com.ycx.lend.service.GPSLogService;
import com.ycx.lend.utils.EmptyChecker;
import com.ycx.lend.utils.GPSUtils;
import com.ycx.lend.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author ycx
 * @Date 2022/1/30 12:49
 * @Description
 */
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarMapper carMapper;
    @Autowired
    StatusMapper statusMapper;
    @Autowired
    CarChangeMapper carChangeMapper;
    @Autowired
    CarTypeMapper carTypeMapper;
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    CompanyService companyService;
    @Autowired
    GPSLogService gpsLogService;


    @Override
    public int updateCar(String carId, Integer carStatus) {
        if (EmptyChecker.isAnyOneEmpty(carId, carStatus)) {
            return 0;
        }
        if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(carId))) {
            return -3;
        }
        if (EmptyChecker.isEmpty(statusMapper.selectByPrimaryKey(carStatus))) {
            return -2;
        }
        Car car = new Car();
        car.setCarId(carId);
        car.setCarStatus(carStatus);
        return carMapper.updateByPrimaryKeySelective(car);
    }

    @Override
    public int addCarChange(String carId, Integer afterStatus, Date changeTime) {
        if (EmptyChecker.isAnyOneEmpty(carId, changeTime, afterStatus)) {
            return 0;
        }
        if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(carId))) {
            return -2;
        }
        if (EmptyChecker.isEmpty(statusMapper.selectByPrimaryKey(afterStatus))) {
            return -2;
        }

        CarChange carChange = new CarChange();
        carChange.setChangeTime(changeTime);
        carChange.setAfterStatus(afterStatus);
        carChange.setCarId(carId);
        carChange.setBeforeStatus(carMapper.selectByPrimaryKey(carId).getCarStatus());
        if (!Objects.equals(carChange.getAfterStatus(), carChange.getBeforeStatus())) {
            return carChangeMapper.insert(carChange);
        }
        return -4;
    }

    @Override
    public int returnCar(String userId, String carId, String time) throws ParseException, ParamException {
        if (EmptyChecker.isAnyOneEmpty(carId, time)) {
            return 0;
        }
        //过滤不存在的车牌号
        if (EmptyChecker.isEmpty(carMapper.selectByPrimaryKey(carId))) {
            return -2;
        }
        //转换输入的string类型时间
        Date date = ServiceUtils.StrToDate(time);
        //查询车辆归还前状态
        Integer carStatus = carMapper.selectByPrimaryKey(carId).getCarStatus();

        //借车申请的还车只有借车者拥有权限
        if (carStatus == 2) {
            if (EmptyChecker.isEmpty(userId))
                return 0;
            Application application = applicationMapper.queryByCarAndUser(carId, userId);
            //调用者是借车者,进行归还操作
            if (EmptyChecker.notEmpty(application)) {
                if (application.getIfReturn() != 0) {
                    return -7;
                }
                //判断汽车位置是否在公司内部
                //获取公司定位
                HashMap<String, Double> companyLocation = companyService.getCompanyLocation();
                //获取汽车定位
                long LongTime = date.getTime();
                JSON carLocation = (JSON) gpsLogService.search(carId, LongTime - 1, LongTime + 1, 1, 1);
                CarGps carGps = JsonToCarGps(carLocation);
                String s = GPSUtils.GpsConvert(carGps.getPositionX(), carGps.getPositionY());
                String[] location = s.split(",");
                double distance = GPSUtils.getDistance(Double.parseDouble(location[0]), Double.parseDouble(location[1]), companyLocation.get("longitude"), companyLocation.get("latitude"));
                if(distance>400){
                    return -9;
                }
                applicationMapper.updateIfReturn(application.getApplicationId(), 1);
            } else return -6;
        }
        //派车申请归还直接修改
        else if (carStatus == 3) {
            Application application = applicationMapper.queryNotReturnByCarId(carId);
            if (EmptyChecker.notEmpty(application)) {
                applicationMapper.updateIfReturn(application.getApplicationId(), 1);
            } else return -4;
        }
        //车辆本就是正常状态，不需要归还
        else return -5;
        Car car = new Car();
        car.setCarId(carId);
        car.setCarStatus(1);
        addCarChange(carId, 1, date);
        return carMapper.updateByPrimaryKeySelective(car);
    }

    @Override
    public List<Car> queryIdleCar() {
        return carMapper.queryIdleCar();
    }

    @Override
    public String queryCarStatusName(Integer carStatus) {
        if (EmptyChecker.isEmpty(carStatus)) {
            return "0";
        }
        if (EmptyChecker.isEmpty(carTypeMapper.selectByPrimaryKey(carStatus))) {
            return "-2";
        }
        return statusMapper.queryCarStatusName(carStatus);
    }

    @Override
    public boolean ifWithinScope(String car) {

        Point carP = new Point();
        Point companyP = new Point();
        return false;
    }

    private CarGps JsonToCarGps(JSON json) {
        FormatData formatData = json.toJavaObject(FormatData.class);
        JSONArray data = (JSONArray) formatData.getData();
        JSON json1 = (JSON) data.get(0);
        HashMap<String, Object> location = JSON.parseObject(json1.toString(), HashMap.class);
        System.out.println(location);
        CarGps carGps = new CarGps();
        BigDecimal positionX = (BigDecimal) location.get("positionX");
        BigDecimal positionY = (BigDecimal) location.get("positionY");
        carGps.setPositionX(positionX);
        carGps.setPositionY(positionY);
        return carGps;
    }
}
