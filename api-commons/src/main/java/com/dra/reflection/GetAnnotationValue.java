package com.dra.reflection;


import com.dra.annotation.Public;
import com.dra.annotation.User;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetAnnotationValue {
    private String methodName;

    @Deprecated
    private Class<?> getClazz_de(Object handler) throws ClassNotFoundException {


        //导入相关类集合
//        HashMap<String, Class<?>> classMap = SecurityIoc.getClassMap();

        String methodMessage = handler.toString();
        System.out.println(methodMessage);
        String methodPath = methodMessage.substring(getCharLocation(methodMessage," ",2),methodMessage.length()-1);
        methodPath = methodPath.substring(0, methodPath.lastIndexOf("("));
        System.out.println(methodPath);
        //分离出类路径与方法名称
        String clazzPath = methodPath.substring(0, methodPath.lastIndexOf("."));
        methodName = methodPath.substring(methodPath.lastIndexOf(".") + 1);
        System.out.println(clazzPath);
        System.out.println(methodName);
        //匹配类路径 获取相关类
        //获取方法对应的类
        return Class.forName(clazzPath);
    }
    private Class<?> getClazz(Object handler) throws ClassNotFoundException {


        //导入相关类集合
//        HashMap<String, Class<?>> classMap = SecurityIoc.getClassMap();

        String methodMessage = handler.toString();
        System.out.println(methodMessage);
        //分离出类路径与方法名称
        String[] split = methodMessage.split("#");
        String clazzPath = split[0];
        System.out.println(clazzPath);
        System.out.println(split[1]);
        methodName = split[1].substring(0,split[1].lastIndexOf("("));
        System.out.println(methodName);


        //匹配类路径 获取相关类
        //获取方法对应的类
        return Class.forName(clazzPath);
    }

    /**
     * 返回 字串在主串中第n次出现第位置
     *
     * @param main 主串
     * @param pat 子串
     * @param num 第几次出现
     * @return 位置
     */

    private int getCharLocation(String main,String pat,int num){
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(main);
        int loc=0;
        for (int i=0;matcher.find();i++){
            if (i==num-1){
                loc = matcher.start();
            }
        }
        return loc;
    }
//    @Test
//    public void getClazz() throws ClassNotFoundException {
//        String methodMessage = "public java.lang.Object com.dra.controller.LoginController.loginUser(java.lang.String,java.lang.String) throws com.dra.exception";
//        //修正路径信息
//        String methodPath = methodMessage.substring(getCharLocation(methodMessage," ",2)+1,methodMessage.length()-1);
//        methodPath = methodPath.substring(0, methodPath.lastIndexOf("("));
//        System.out.println(methodPath);
//        //分离出类路径与方法名称
//        String clazzPath = methodPath.substring(0, methodPath.lastIndexOf("."));
//        methodName = methodPath.substring(methodPath.lastIndexOf(".") + 1);
//        System.out.println(clazzPath);
//        System.out.println(methodName);
////        Class<?> aClass = Class.forName("com.dra.controller.TestController.test");
//        //获取方法对应的类
////        return Class.forName(clazzPath);
//    }

    public String getRequestUrl(HttpServletRequest request, Object handler) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = getClazz(handler);
        //获取类上的requestMapping注解
        RequestMapping clazzRequestMapping = clazz.getDeclaredAnnotation(RequestMapping.class);
        String[] fValue = new String[0];
        //判断是否有此注解
        if (clazzRequestMapping != null) {
            //获取请求父路径
            fValue = clazzRequestMapping.value();
        }
        System.out.println("fUrl:" + fValue[0]);
        //匹配请求的方法
        Method method = null;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName))
                method=declaredMethod;
        }

//        System.out.println("methodName:" + declaredMethod.getName());
        //获取请求子路径
        assert method != null;
        RequestMapping methodRequestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        String[] sValue = methodRequestMapping.value();
//        System.out.println("sUrl:" + sValue[0]);
        //总路径
        if (!fValue[0].startsWith("/"))
            fValue[0] = "/" + fValue[0];
        if (!sValue[0].startsWith("/"))
            sValue[0] = "/" + sValue[0];

        String url = request.getContextPath() + fValue[0] + sValue[0];

        System.out.println("url:" + url);

        return url;
    }

    /**
     * 检测url接口是否为公开的
     *
     */
    public boolean isPublic(Object handler) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = getClazz(handler);
        Public aPublicClass = clazz.getDeclaredAnnotation(Public.class);
        if (aPublicClass != null) {
            return true;
        }
        System.out.println(methodName);
        //匹配请求的方法
        Method method = null;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName))
                method=declaredMethod;
        }

        assert method != null;
        Public aPublicMethod = method.getDeclaredAnnotation(Public.class);

        if (aPublicMethod != null) {
            return true;
        }

        return false;
    }
    /**
     * 检测url接口是否为普通用户的
     *
     */
    public boolean isUser(Object handler) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = getClazz(handler);
        User aPublicClass = clazz.getDeclaredAnnotation(User.class);
        if (aPublicClass != null) {
            return true;
        }

        //匹配请求的方法
        Method method = null;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName))
                method=declaredMethod;
        }
        assert method != null;
        User aPublicMethod = method.getDeclaredAnnotation(User.class);

        if (aPublicMethod != null) {
            return true;
        }

        return false;
    }
}
