package com.wang.vire.service;

import com.wang.vire.pojo.Car;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/30 12:49
 * @Description
 */
public interface CarService {

    //申请通过终审时修改汽车状态
    int updateCar(String carId,Integer carStatus);

    //存放汽车状态变化时间
    int addCarChange(String carId, Integer afterStatus, Date changeTime);

    //查询闲置车辆
    List<Car> queryIdleCar();

    //根据车辆状态码查询车辆状态描述
    String queryCarStatusName(Integer carStatus);

}
