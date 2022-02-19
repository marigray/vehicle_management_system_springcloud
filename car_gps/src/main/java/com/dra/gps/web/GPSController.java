package com.dra.gps.web;

import com.dra.gps.exception.ParamException;
import com.dra.gps.service.GPSService;
import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/gps")
public class GPSController {
    @Resource
    private GPSService gpsService;

    private final Object success = new ResponseFormat().getSuccess("success", 250, null);

    private final ResponseFormat successData = new ResponseFormat();



    private void isError(int i) throws ParamException {
        if (i==0)
            throw new ParamException();
    }
    @RequestMapping("/add.do")
    public Object add(@RequestBody Gps gps) throws ParamException{
        int add = gpsService.add(gps);
        isError(add);
        return success;
    }

    @RequestMapping("/del.do")
    public Object delete(String gpsId) throws ParamException{
        int delete = gpsService.delete(gpsId);
        isError(delete);
        return success;
    }

    @RequestMapping("/up.do")
    public Object update(@RequestBody Gps gps) throws ParamException{
        int update = gpsService.update(gps);
        isError(update);
        return success;
    }
    @RequestMapping("/search.do")
    public Object search(String gpsId, int pageNum, int pageSize) throws ParamException {
        ArrayList<Gps> list = gpsService.search(gpsId,pageNum,pageSize);
        return successData.getSuccess(list);
    }
    @RequestMapping("/search_car.do")
    public Object searchCarByGpsId(String gpsId) throws ParamException{
        Car car = gpsService.searchCarByGpsId(gpsId);
        if (car==null)
            throw new ParamException();
        return successData.getSuccess(car);
    }
}
