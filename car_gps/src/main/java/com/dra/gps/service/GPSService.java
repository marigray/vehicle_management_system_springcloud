package com.dra.gps.service;

import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;

import java.util.ArrayList;

public interface GPSService {
    int add(Gps gps);
    int delete(String gpsId);
    int update(Gps gps);
    ArrayList<Gps> search(String gpsId, int pageNum, int pageSize);

    Car searchCarByGpsId(String gpsId);
}
