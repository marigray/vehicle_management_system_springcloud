package com.dra.gps.web;

import com.dra.annotation.Public;
import com.dra.gps.exception.ParamException;
import com.dra.gps.service.GPSLogService;
import com.dra.pojo.gps.GpsLog;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("gps_log")
public class GPSLogController {
    @Resource
    private GPSLogService gpsLogService;

    private final Object success = new ResponseFormat().getSuccess("success", 250, null);

    private final ResponseFormat successData = new ResponseFormat();



    private void isError(int i) throws ParamException {
        if (i==0)
            throw new ParamException();
    }
    @RequestMapping("/add.do")
    public Object add(@RequestBody GpsLog gpsLog) throws ParamException{
        gpsLog.setTime(new Date());
        int add = gpsLogService.add(gpsLog);
        isError(add);
        return success;
    }

    @Public
    @RequestMapping("/search.do")
    public Object search(String carId,
                         long date1,
                         long date2,
                         int pageNum,
                         int pageSize) throws ParamException {
        if (carId==null||carId.equals(""))
            throw new ParamException();
        Date dateStart = new Date(date1);
        Date dateEnd = new Date(date2);
        ArrayList<GpsLog> list = gpsLogService.search(carId,dateStart,dateEnd,pageNum,pageSize);
        return successData.getSuccess(list);
    }
}
