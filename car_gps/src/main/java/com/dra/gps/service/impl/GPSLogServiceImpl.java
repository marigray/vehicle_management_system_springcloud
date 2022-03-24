package com.dra.gps.service.impl;

import com.dra.gps.mapper.GPSLogMapper;
import com.dra.gps.service.GPSLogService;
import com.dra.pojo.gps.GpsLog;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

@Service
public class GPSLogServiceImpl implements GPSLogService {
    @Resource
    private GPSLogMapper gpsLogMapper;

    public boolean checkObject(GpsLog gpsLog) {
        return gpsLog == null || gpsLog.getGpsId() == null || gpsLog.getGpsId().equals("");
    }

    @Override
    public int add(GpsLog gpsLog) {
        if (checkObject(gpsLog))
            return 0;
        return gpsLogMapper.add(gpsLog);
    }


    @Override
    public ArrayList<GpsLog> search(String carId,
                                    Date date1,
                                    Date date2,
                                    int pageNum,
                                    int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(gpsLogMapper.search(carId, date1, date2));
        return gpsLogMapper.search(carId, date1, date2);
    }
}
