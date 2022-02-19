package com.dra.gps.service.impl;

import com.dra.gps.mapper.CarGPSMapper;
import com.dra.gps.service.CarGpsService;
import com.dra.pojo.gps.CarGps;
import com.dra.utils.IsNullCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CarGpsServiceImpl implements CarGpsService {

    @Resource
    private CarGPSMapper carGPSMapper;

    public boolean checkObject(CarGps carGps){
        return (carGps == null || carGps.getCarId() == null || carGps.getCarId().equals("")||
                carGps.getGpsId()==null || carGps.getGpsId().equals(""));
    }

    @Override
    public int add(CarGps carGps) {
        if (checkObject(carGps))
            return 0;
        return carGPSMapper.add(carGps);
    }

    @Override
    public int delete(String carId) {
        if (IsNullCheck.isNullOfString(carId))
            return 0;
        return carGPSMapper.delete(carId);
    }

    @Override
    public int update(CarGps carGps) {
        if (IsNullCheck.isNullOfString(carGps.getCarId())
                ||IsNullCheck.isNullOfString(carGps.getGpsId()))
            return 0;
        return carGPSMapper.update(carGps);
    }

    @Override
    public Page<CarGps> search(String carId, int pageNum, int pageSize) {
        int [] ints = {pageNum,pageSize};
        if (!IsNullCheck.isZeroIntArray(ints))
            PageHelper.startPage(pageNum,pageSize);
        return carGPSMapper.search(carId);
    }
}
