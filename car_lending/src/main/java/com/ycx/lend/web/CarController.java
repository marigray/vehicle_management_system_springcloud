package com.ycx.lend.web;

import com.ycx.lend.exception.ParamException;
import com.ycx.lend.pojo.Car;
import com.dra.pojo.msg.FormatData;
import com.ycx.lend.service.CarService;
import com.ycx.lend.utils.WebCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 * @Author ycx
 * @Date 2022/1/30 21:13
 * @Description
 */
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @RequestMapping("/queryIdleCar.do")
    public FormatData<Object> queryIdleCar() throws ParamException {
        List<Car> cars = carService.queryIdleCar();
        WebCheck.isError(cars);
        return new FormatData<>(cars);
    }

    @RequestMapping("/returnCar.do")
    public FormatData<Object> returnCar(@RequestParam("userId") String userId,
                                        @RequestParam("carId") String carId,
                                        @RequestParam("time") String time) throws ParamException, ParseException {
        int i = carService.returnCar(userId, carId, time);
        WebCheck.isError(i);
        return new FormatData<>(i);
    }

    @RequestMapping("/queryCarStatusName.do")
    public FormatData<Object> queryCarStatusName(@RequestParam("carStatus") Integer carStatus) throws ParamException {
        String s = carService.queryCarStatusName(carStatus);
        WebCheck.isError(s);
        return new FormatData<>(s);
    }
}
