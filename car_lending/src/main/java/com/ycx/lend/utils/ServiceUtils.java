package com.ycx.lend.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author ycx
 * @Date 2022/1/24 13:51
 * @Description
 */
@Component
public class ServiceUtils {

    public static String SetPrimaryKey(String s) {
        if (EmptyChecker.notEmpty(s)) {
            return String.valueOf(1 + Integer.parseInt(s));
        } else {
            return "1";
        }
    }

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

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-[1-9]*|0");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
