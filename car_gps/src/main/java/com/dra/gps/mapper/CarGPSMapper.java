package com.dra.gps.mapper;

import com.dra.pojo.gps.CarGps;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarGPSMapper {
    int add(CarGps carGps);
    int delete(@Param("carId") String carId);
    int update(CarGps carGps);
    Page<CarGps> search(@Param("carId") String carId);
}
