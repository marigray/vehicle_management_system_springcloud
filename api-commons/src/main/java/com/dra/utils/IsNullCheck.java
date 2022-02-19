package com.dra.utils;

public class IsNullCheck {
    public static boolean isNullOfString(String [] strings){
        for (String string : strings) {
            if (string == null || string.equals(""))
                return true;
        }
        return false;
    }
    public static boolean isNullOfString(String str){
        return str == null || str.equals("");
    }
    public static boolean isZeroIntArray(int [] ints){
        for (int anInt : ints) {
            if (anInt == 0)
                return true;
        }
        return false;
    }
}
