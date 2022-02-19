package com.dra.pojo.gps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private int longitude; //度
    private int minute;    //分
    private double second;    //秒


    public Location LocationTransformation(String location){
        BigDecimal locationDouble = new BigDecimal(location);
        longitude = (int) (locationDouble.doubleValue()/100);
        minute = (int) (locationDouble.doubleValue()%100);
        //使用BigDecimal防止精度丢失
        second = new BigDecimal(String.valueOf(locationDouble.subtract(new BigDecimal(longitude*100+minute)))).
                multiply(new BigDecimal(60)).doubleValue();
        return this;
    }

//    @Test
    public void myTest() {
        Location location = new Location().LocationTransformation("2318.13307");
        System.out.println(location);
//        System.out.println(new BigDecimal("0.13307").multiply(new BigDecimal("60")).doubleValue());
    }
}
