package com.dra.gps.exception;

public class SqlDataNotFound extends Exception {

    private String message;
    private int code;


    public SqlDataNotFound(String message,int code) {
        this.message=message;
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
