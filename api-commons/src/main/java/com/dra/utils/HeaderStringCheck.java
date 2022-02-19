package com.dra.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class HeaderStringCheck {

    /**
     *
     * @param msg 请求头字符串信息
     * @return 是否为固定格式
     *    *GPS#{GPS的id}*
     */
    public static boolean isHead(String msg){
        //清洗
        msg = msg.replace("\n","").replace("\r","");
        String pat = "\\*([\\S]+)\\#([\\S]+)\\*";
        if (Pattern.matches(pat,msg)){
            HashMap<String, String> head = getHead(msg);
            return head.get("type").equals("GPS");
        }
        return false;
    }
    public static  boolean isWSHead(String msg){
        String pat = "\\*[\\S]+\\*";
        return Pattern.matches(pat,msg);
    }
    /**
     *
     * @param msg 请求头字符串信息
     * @return 信息分割
     */
    public static HashMap<String,String> getHead(String msg){

        String[] split = msg.replace("*", "").split("#");
        HashMap<String,String> msg_map = new HashMap<>();
        msg_map.put("type",split[0]);
        msg_map.put("gpsId",split[1]);
        return msg_map;
    }

    public static String getWSHead(String msg){
        return msg.replace("*","");
    }
    public static void main(String[] args) {
        System.out.println(isHead("*GPS#gpsid*\r\n"));
    }
}
