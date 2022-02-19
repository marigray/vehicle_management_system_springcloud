package com.dra.utils;

import java.util.regex.Pattern;

public class CheckTool {
    public static boolean Mail(String msg){
        String pat = "[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+";
        return Pattern.matches(pat,msg);
    }
}
