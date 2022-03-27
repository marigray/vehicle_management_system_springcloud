package com.wang.vire.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dra.pojo.msg.FormatData;
import com.github.pagehelper.Page;
import com.ycx.lend.pojo.Audit;
import com.ycx.lend.pojo.Car;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @Author wang
 * @Data 2022/3/2 18:17
 * @Description
 */
public class JsonUtils {
    public static <T> T JsonToPojo(Object param, T pojo){
        JSON param1 = (JSON) param;
        FormatData o = JSON.parseObject(param1.toString(),FormatData.class);
        JSON o1 = (JSON)(o.getData());
        Object o2=null;
        if(EmptyChecker.notEmpty(o1)){
            o2 = JSON.parseObject(o1.toString(),(Type)pojo);
        }
        return (T)o2;
    }
    public static <T> List<T> JsonToListPojo(Object param, T pojo){
        List<T> lists=new Page<>();
        JSON o = (JSON)param;
        FormatData formatData = o.toJavaObject(FormatData.class);
        String s = JSONObject.toJSONString(formatData.getData());
        JSONArray jsonArray=JSONArray.parseArray(s);
//        if(EmptyChecker.isEmpty(jsonArray)){
//            return null;
//        }
        for(int i=0;i<jsonArray.size();i++){
            JSON object= (JSON) jsonArray.get(i);
            lists.add(JSONObject.parseObject(object.toString(),(Type) pojo));
        }
//        for (Object value : jsonArray) {
//            JSONObject object = (JSONObject) value;
//            lists.add(JSONObject.parseObject(object.toString(),(Type) pojo));
//        }
        return lists;
    }
    public static List<String> JsonToListString(Object param){
        List<String> lists=new Page<>();
        JSON o = (JSON)param;
        FormatData formatData = o.toJavaObject(FormatData.class);
        String s = JSONObject.toJSONString(formatData.getData());
        JSONArray jsonArray=JSONArray.parseArray(s);
        for(int i=0;i<jsonArray.size();i++){
            String object=(String)jsonArray.get(i);
            lists.add(object);
        }
        return lists;
    }
    public static int JsonToInt(Object param){
        JSON param1 = (JSON) param;
        FormatData o = param1.toJavaObject(FormatData.class);
        Object o1 = (o.getData());
        int o2 = 0;
        if(EmptyChecker.notEmpty(o1)){
            o2 =(int) o1;
        }
        return o2;
    }
    public static String JsonToString(Object param){
        JSON param1 = (JSON) param;
        FormatData o = param1.toJavaObject(FormatData.class);
        Object o1 = (o.getData());
        String o2 = null;
        if(EmptyChecker.notEmpty(o1)){
            o2 =(String) o1;
        }
        return o2;
    }
}
