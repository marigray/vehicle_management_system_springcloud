package com.dra.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应值标准化
 */

public class ResponseFormat{

    public ResponseData getError(){
        return new ResponseData("服务内部错误");
    }
    public ResponseData getError(String message){
        return new ResponseData(message);
    }
    public ResponseData getError(String message,int code){
        return new ResponseDataForCode(message,code);
    }
    public ResponseData getSuccess(){
        return new ResponseData("ok");
    }
    public ResponseData getSuccess(Object data){
        return new ResponseDataForData("ok",data);
    }

    public ResponseData getSuccess(String message,String data){
        return new ResponseDataForData(message,data);
    }

    public ResponseData getSuccess(String message,int code,Object data){
        return new ResponseDataForDataAndCode(message,data,code);
    }


}

@Data
@AllArgsConstructor
class ResponseData{
    private String message;
}

/**
 * 有数据版
 */
class ResponseDataForData extends ResponseData{
    private Object data;


    public ResponseDataForData(String message, Object data) {
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

/**
 * 带状态码
 */
class ResponseDataForCode extends ResponseData{

    private int code;

    public ResponseDataForCode(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

/**
 * 带状态码和数据
 */
class ResponseDataForDataAndCode extends ResponseDataForData{
    private int code;

    public ResponseDataForDataAndCode(String message, Object data, int code) {
        super(message, data);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}