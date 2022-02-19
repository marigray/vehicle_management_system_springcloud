package com.dra.gps.exception;


/**
 * 参数报错类
 */

public class ParamException extends Exception{


    private int code;
    private String message;

    public ParamException(String message, int code) {
        this.code = code;
        this.message = message;
    }
    public ParamException() {
        this.code = 405;
        this.message = "参数有误或者无数据";
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
