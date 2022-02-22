package com.wang.vire.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author wang
 * @Date 2022/1/25 10:23
 * @Description
 */
@Component
public class ServiceUtils {

    //将Long转换为int
    public static Integer NumberToInt(Object l){
        return ((Number) l).intValue();
    }

    //将String转Date
    public static Date StrToDate(String srt) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换为util包的日期
        return formatter.parse(srt);
    }
    //将Java的CST格式的时间字符串转为为Date对象和所需要的日期时间格式！
    public static String CstToDate(String srt) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(srt);
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
        return formatDate;
    }
}
