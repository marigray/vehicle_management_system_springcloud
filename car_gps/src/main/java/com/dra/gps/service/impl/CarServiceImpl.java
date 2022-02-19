package com.dra.gps.service.impl;

import com.dra.gps.mapper.CarMapper;
import com.dra.gps.service.CarService;
import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;
import com.dra.utils.IsNullCheck;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    @Resource
    private CarMapper carMapper;

    public boolean checkObject(Car car){
        return car == null || car.getCarId() == null || car.getCarId().equals("");
    }
    @Override
    public int add(Car car) {
        car.setCarId(UUID.randomUUID().toString());
        return carMapper.add(car);
    }

    @Override
    public int delete(String carId) {
        if (IsNullCheck.isNullOfString(carId))
            return 0;
        return carMapper.delete(carId);
    }

    @Override
    public int update(Car car) {
        if (checkObject(car))
            return 0;
        return carMapper.update(car);
    }

    @Override
    public ArrayList<Car> search(String carId, int pageNum, int pageSize) {
        int [] ints = {pageNum,pageSize};
        if (!IsNullCheck.isZeroIntArray(ints))
            PageHelper.startPage(pageNum,pageSize);
        return carMapper.search(carId);
    }

    @Override
    public Gps searchGpsByCarId(String carId) {
        if (IsNullCheck.isNullOfString(carId))
            return null;
        return carMapper.searchGpsByCarId(carId);
    }

}
