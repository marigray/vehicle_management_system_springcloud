package com.dra.utils;


import com.dra.pojo.gps.GpsLog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        return new GpsLog(carId, gpsId, date, Double.parseDouble(var1[4]), Double.parseDouble(var1[2]));
    }

    //数据清洗
    public static GpsLog dataWash(GpsLog gpsLog) {
        //修正
        gpsLog.setPositionX(wash(gpsLog.getPositionX()-0.111282));
        gpsLog.setPositionY(wash(gpsLog.getPositionY())-0.0205663);
        return gpsLog;
    }

    private static double wash(double var1) {
        int de;
        int abc;
        double fghi;

        String var2 = String.valueOf(var1);
        System.out.println(var2);
        String[] var3 = var2.split("\\.");

        fghi = Double.parseDouble(var3[1]);
        int v = Integer.parseInt(var3[0]);
        de = v % 100;
        abc = v / 100;
//        System.out.println("de="+de);
//        System.out.println("abc="+abc);
//        System.out.println("fghi="+fghi);
        var1 = abc + de / 60.0 + fghi / (Math.pow(10, 5) * 6);
//        DecimalFormat decimalFormat = new DecimalFormat("#.000000");
//
//        System.out.println("var1="+var1);
//        String[] var4 =  (String.valueOf(var1)).split("\\.");
//        //取小数后6位
//        StringBuilder var5 = new StringBuilder();
//        char[] var6 = var4[1].toCharArray();
//        for (int i=0;i<6;i++){
//            var5.append(var6[i]);
//        }
//        System.out.println(var5);
//        Double.parseDouble(var4[0]+"."+var5)
//        return Double.parseDouble(decimalFormat.format(var1));
        return var1;
    }

    public static void main(String[] args) {
        GpsLog gpsLog = new GpsLog();
        gpsLog.setPositionY(2859.15489);
        gpsLog.setPositionX(11851.82449);
        System.out.println(new GPSMessage().dataWash(gpsLog));
        System.out.println(113 + 59 / 60.0 + 8697 / 600000.0);
        //118.876133,28.988582
        //误差：-0.111282,-0.0205663
    }
}
