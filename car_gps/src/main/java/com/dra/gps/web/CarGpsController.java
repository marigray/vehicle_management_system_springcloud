package com.dra.gps.web;

import com.dra.gps.exception.ParamException;
import com.dra.gps.service.CarGpsService;
import com.dra.pojo.gps.CarGps;
import com.dra.utils.ResponseFormat;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/car_gps")
public class CarGpsController {
    @Resource
    private CarGpsService carGpsService;
    private final Object success = new ResponseFormat().getSuccess("success", 250, null);

    private final ResponseFormat successData = new ResponseFormat();



    private void isError(int i) throws ParamException {
        if (i==0)
            throw new ParamException();
    }
    @RequestMapping("/add.do")
    public Object add(@RequestBody CarGps carGps) throws ParamException{
        int add = carGpsService.add(carGps);
        isError(add);
        return success;
    }

    @RequestMapping("/del.do")
    public Object delete(String carId) throws ParamException{
        int delete = carGpsService.delete(carId);
        isError(delete);
        return success;
    }

    @RequestMapping("/up.do")
    public Object update(@RequestBody CarGps carGps) throws ParamException{
        int update = carGpsService.update(carGps);
        isError(update);
        return success;
    }

    @RequestMapping("/search.do")
    public Object search(String carId, int pageNum, int pageSize) throws ParamException{
        Page<CarGps> list = carGpsService.search(carId,pageNum,pageSize);
        return successData.getSuccess(list);
    }
}
