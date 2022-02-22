package com.ycx.lend.exception;

/**
 * 参数报错类
 */

public class ParamException extends Exception{

    private final int code;

    private final String message;

    public ParamException(String message, int code) {
        this.code = code;
        this.message = message;
    }
    public ParamException() {
        this.code = 405;
        this.message = "参数有误";
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
