package com.dra.gps.mapper;

import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarMapper {
    int add(Car car);
    int delete(@Param("carId") String carId);
    int update(Car car);
    Page<Car> search(@Param("carId") String carId);

    Gps searchGpsByCarId(@Param("carId") String carId);
}
