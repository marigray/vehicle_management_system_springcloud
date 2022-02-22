package com.wang.vire.service.impl;

import com.wang.vire.mapper.CarChangeMapper;
import com.wang.vire.mapper.CarMapper;
import com.wang.vire.mapper.CarTypeMapper;
import com.wang.vire.mapper.StatusMapper;
import com.wang.vire.pojo.Car;
import com.wang.vire.pojo.CarChange;
import com.wang.vire.service.CarService;
import com.wang.vire.utils.EmptyChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    CarMapper carMapper;
    @Autowired
    StatusMapper statusMapper;
    @Autowired
    CarChangeMapper carChangeMapper;
    @Autowired
    CarTypeMapper carTypeMapper;


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
}
