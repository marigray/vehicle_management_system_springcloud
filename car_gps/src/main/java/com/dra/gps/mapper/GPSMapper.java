package com.dra.gps.mapper;

import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GPSMapper {
    int add(Gps gps);
    int delete(@Param("gpsId") String gpsId);
    int update(Gps gps);
    Page<Gps> search(@Param("gpsId") String gpsId);

    Car searchCarByGpsId(@Param("gpsId") String gpsId);
}
