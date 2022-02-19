package com.dra.gps.service;

import com.dra.pojo.gps.CarGps;
import com.github.pagehelper.Page;

public interface CarGpsService {
    int add(CarGps carGps);
    int delete(String carId);
    int update(CarGps carGps);
    Page<CarGps> search(String carId,int pageNum,int pageSize);
}
