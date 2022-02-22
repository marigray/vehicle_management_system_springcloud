package com.ycx.lend.utils;


import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @Author ycx
 * @Date 2022/2/9 21:28
 * @Description
 */
public class GPSUtils {
    private static final double EARTH_RADIUS = 6378.137;//地球半径,单位千米

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param lat1 第一纬度
     * @param lng1 第一经度
     * @param lat2 第二纬度
     * @param lng2 第二经度
     * @return 两坐标之间的距离
     */
    public static double getDistance(double lat1,double lng1,double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10;
        return s;
    }

    public static String GpsConvert(String location){
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String,String> ret = restTemplate.getForObject("https://restapi.amap.com/v3/assistant/coordinate/convert?locations={location}&coordsys=gps&output=JSON&key=af856185a75304af38e202a35f7bbb0b"
                , HashMap.class,location);
        assert ret != null;
        return ret.get("locations");
    }


}
