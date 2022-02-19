package com.dra.msg.mapper;

import com.dra.pojo.msg.Car;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarMapper {
    int addCar(Car car);
    int delCar(@Param("carId") String carId);
    int update(Car car);
    Car select(@Param("carId") String carId);
    Page<Car> selectAll();
}
