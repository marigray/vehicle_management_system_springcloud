package com.dra.gps.mapper;

import com.dra.pojo.gps.GpsLog;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface GPSLogMapper {
    int add(GpsLog gpsLog);
    Page<GpsLog> search(@Param("carId") String carId,
                     @Param("date1") Date date1,
                     @Param("date2") Date date2);
}
