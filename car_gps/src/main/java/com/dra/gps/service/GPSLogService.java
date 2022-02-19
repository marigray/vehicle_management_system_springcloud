package com.dra.gps.service;

import com.dra.pojo.gps.GpsLog;

import java.util.ArrayList;
import java.util.Date;

public interface GPSLogService {
    int add(GpsLog gpsLog);
    ArrayList<GpsLog> search(String carId,
                             Date date1,
                             Date date2,
                             int pageNum,
                             int pageSize);
}
