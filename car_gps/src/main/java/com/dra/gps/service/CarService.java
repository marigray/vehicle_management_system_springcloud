package com.dra.gps.service;


import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;

import java.util.ArrayList;

public interface CarService {
    int add(Car car);
    int delete(String carId);
    int update(Car car);
    ArrayList<Car> search(String carId,int pageNum,int pageSize);

    Gps searchGpsByCarId(String carId);
}
