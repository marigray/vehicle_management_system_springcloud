package com.dra.gps.web;

import com.dra.gps.exception.ParamException;
import com.dra.gps.service.CarService;
import com.dra.pojo.gps.Car;
import com.dra.pojo.gps.Gps;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/car")
public class CarController {

    @Resource
    private CarService carService;

    private final Object success = new ResponseFormat().getSuccess("success", 250, null);

    private final ResponseFormat successData = new ResponseFormat();



    private void isError(int i) throws ParamException {
        if (i==0)
            throw new ParamException();
    }
    @RequestMapping("/add.do")
    public Object add(@RequestBody Car car) throws ParamException{
        int add = carService.add(car);
        isError(add);
        return success;
    }

    @RequestMapping("/del.do")
    public Object delete(String carId) throws ParamException{
        int delete = carService.delete(carId);
        isError(delete);
        return success;
    }

    @RequestMapping("/up.do")
    public Object update(@RequestBody Car car) throws ParamException{
        int update = carService.update(car);
        isError(update);
        return success;
    }
    @RequestMapping("/search.do")
    public Object search(String carId, int pageNum, int pageSize) throws ParamException{
        ArrayList<Car> list = carService.search(carId,pageNum,pageSize);
        return successData.getSuccess(list);
    }
    @RequestMapping("/search_gps.do")
    public Object searchGpsByCarId(String carId) throws ParamException{
        Gps gps = carService.searchGpsByCarId(carId);
        if (gps==null)
            throw new ParamException();
        return successData.getSuccess(gps);
    }
}
