package com.dra.gps.service.impl;

import com.dra.gps.mapper.GPSMapper;
import com.dra.gps.service.GPSService;
import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;
import com.dra.utils.IsNullCheck;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class GPSServiceImpl implements GPSService {
    @Resource
    private GPSMapper gpsMapper;

    public boolean checkObject(Gps gps){
        return gps == null || gps.getGpsId() == null || gps.getGpsId().equals("");
    }
    @Override
    public int add(Gps gps) {
        gps.setGpsId(UUID.randomUUID().toString());
        return gpsMapper.add(gps);
    }

    @Override
    public int delete(String gpsId) {
        if (IsNullCheck.isNullOfString(gpsId))
            return 0;
        return gpsMapper.delete(gpsId);
    }

    @Override
    public int update(Gps gps) {
        if (checkObject(gps))
            return 0;
        return gpsMapper.update(gps);
    }

    @Override
    public ArrayList<Gps> search(String gpsId, int pageNum, int pageSize) {
        int [] ints = {pageNum,pageSize};
        if (!IsNullCheck.isZeroIntArray(ints))
            PageHelper.startPage(pageNum,pageSize);
        return gpsMapper.search(gpsId);
    }

    @Override
    public Car searchCarByGpsId(String gpsId) {
        if (IsNullCheck.isNullOfString(gpsId))
            return null;
        return gpsMapper.searchCarByGpsId(gpsId);
    }
}
