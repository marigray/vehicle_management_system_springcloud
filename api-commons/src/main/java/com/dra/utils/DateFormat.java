package com.dra.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化
 */
public class DateFormat {


    public static String getFormatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }
}
