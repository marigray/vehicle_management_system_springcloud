package com.dra.utils;


import com.dra.pojo.gps.GpsLog;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GPSMessage {

    private final Map<String, String> gpsMessage = new HashMap<>();

    //解析信息并分类
    public static GPSMessage analysisMessage(String msg) {
        GPSMessage gpsMessage = new GPSMessage();
        String[] var1 = msg.split("\n");
        for (int i = 0; i < var1.length; i++) {
            String[] var2 = var1[i].split(",");
            gpsMessage.gpsMessage.put(var2[0], var1[i]);
        }
        return gpsMessage;
    }


    //解析出位置信息 N E
    public GpsLog analysisPositionMessage(String carId, String gpsId) {
        Date date = new Date();
        String[] var1 = this.gpsMessage.get(FinalValueSet.GNGGA_HEADER).split(",");

//        System.out.println(Arrays.toString(var1));
        return new GpsLog(carId, gpsId, date, Double.parseDouble(var1[4]), Double.parseDouble(var1[2]));
    }

    //数据清洗
    public static GpsLog dataWash(GpsLog gpsLog) {
        //修正
        gpsLog.setPositionX(wash(gpsLog.getPositionX()));
        gpsLog.setPositionY(wash(gpsLog.getPositionY()));
        return gpsLog;
    }

//    private static double wash(double var1) {
//        int de;
//        int abc;
//        double fghi;
//
//        String var2 = String.valueOf(var1);
//        System.out.println(var2);
//        String[] var3 = var2.split("\\.");
//
//        fghi = Double.parseDouble(var3[1]);
//        int v = Integer.parseInt(var3[0]);
//        de = v % 100;
//        abc = v / 100;
////        System.out.println("de="+de);
////        System.out.println("abc="+abc);
////        System.out.println("fghi="+fghi);
//        var1 = abc + de / 60.0 + fghi / (Math.pow(10, 5) * 6);
////        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
////
////        System.out.println("var1="+var1);
////        String[] var4 =  (String.valueOf(var1)).split("\\.");
////        //取小数后6位
////        StringBuilder var5 = new StringBuilder();
////        char[] var6 = var4[1].toCharArray();
////        for (int i=0;i<6;i++){
////            var5.append(var6[i]);
////        }
////        System.out.println(var5);
////        Double.parseDouble(var4[0]+"."+var5)
////        return Double.parseDouble(decimalFormat.format(var1));
//        return var1;
//    }
    private static double wash(double var1){

        int de;
        int abc;
        double fghi;
//        格式化
        var1 = new BigDecimal(var1).divide(new BigDecimal(1),4,BigDecimal.ROUND_HALF_UP).doubleValue();
        String var2 = String.valueOf(var1);
//        System.out.println(var2);
        String[] var3 = var2.split("\\.");

        fghi = Double.parseDouble(var3[1]);
        int v = Integer.parseInt(var3[0]);
        de = v % 100;
        abc = v / 100;
        BigDecimal de_big = new BigDecimal(de);
        BigDecimal abc_big = new BigDecimal(abc);
        BigDecimal fghi_big = new BigDecimal(fghi);
        BigDecimal var1_big = de_big.divide(new BigDecimal(60),9,BigDecimal.ROUND_HALF_UP);
        BigDecimal var2_big = fghi_big.divide(new BigDecimal(600000),12,BigDecimal.ROUND_HALF_UP);
//        var1 = abc + de / 60.0 + fghi / (Math.pow(10, 5) * 6);
        var1 = abc_big.add(var1_big).add(var2_big).doubleValue();
        return var1;




    }
    public static void main(String[] args) {
        GpsLog gpsLog = new GpsLog();
        gpsLog.setPositionY(2859.14999);
        gpsLog.setPositionX(11851.81522);
        System.out.println(new GPSMessage().dataWash(gpsLog));
    }
}
