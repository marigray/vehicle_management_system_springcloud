package com.wang.vire.service.impl;

import com.wang.vire.service.CarService;
import com.wang.vire.service.WangService;
import com.wang.vire.utils.EmptyChecker;
import com.wang.vire.utils.JsonUtils;
import com.ycx.lend.pojo.Car;
import com.ycx.lend.pojo.CarChange;
import com.ycx.lend.pojo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    WangService wangService;


    @Override
    public int updateCar(String carId, Integer carStatus) {
        if (EmptyChecker.isAnyOneEmpty(carId, carStatus)) {
            return 0;
        }
        Object carSelByKey = JsonUtils.JsonToPojo(wangService.carSelByKey(carId), Car.class);
        Car carSelByKey1 = (Car) carSelByKey;
        if (EmptyChecker.isEmpty(carSelByKey1)) {
            return -3;
        }
        Object statusSelByKey = JsonUtils.JsonToPojo(wangService.statusSelByKey(carStatus), Status.class);
        Status statusSelByKey1 = (Status) statusSelByKey;
        if (EmptyChecker.isEmpty(statusSelByKey1)) {
            return -2;
        }
        Car car = new Car();
        car.setCarId(carId);
        car.setCarStatus(carStatus);
        Object carUpdSelective = JsonUtils.JsonToInt(wangService.carUpdSelective(car));
        int carUpdSelective1 = (int) carUpdSelective;
        return carUpdSelective1;
    }

    @Override
    public int addCarChange(String carId, Integer afterStatus, Date changeTime) {
        if (EmptyChecker.isAnyOneEmpty(carId, changeTime, afterStatus)) {
            return 0;
        }
        Object carSelByKey = JsonUtils.JsonToPojo(wangService.carSelByKey(carId), Car.class);
        Car carSelByKey1 = (Car) carSelByKey;
        if (EmptyChecker.isEmpty(carSelByKey1)) {
            return -2;
        }
        Object statusSelByKey = JsonUtils.JsonToPojo(wangService.statusSelByKey(afterStatus), Status.class);
        Status statusSelByKey1 = (Status) statusSelByKey;
        if (EmptyChecker.isEmpty(statusSelByKey1)) {
            return -2;
        }

        CarChange carChange = new CarChange();
        carChange.setChangeTime(changeTime);
        carChange.setAfterStatus(afterStatus);
        carChange.setCarId(carId);
        carChange.setBeforeStatus(carSelByKey1.getCarStatus());
        if (!Objects.equals(carChange.getAfterStatus(), carChange.getBeforeStatus())) {
            Object carChangeInsert = JsonUtils.JsonToInt(wangService.carChangeInsert(carChange));
            int carChangeInsert1 = (int) carChangeInsert;
            return carChangeInsert1;
        }
        return -4;
    }

//    @Override
//    public List<Car> queryIdleCar() {
//        return carMapper.queryIdleCar();
//    }

//    @Override
//    public String queryCarStatusName(Integer carStatus) {
//        if (EmptyChecker.isEmpty(carStatus)) {
//            return "0";
//        }
//        if (EmptyChecker.isEmpty(carTypeMapper.selectByPrimaryKey(carStatus))) {
//            return "-2";
//        }
//        return statusMapper.queryCarStatusName(carStatus);
//    }
}
