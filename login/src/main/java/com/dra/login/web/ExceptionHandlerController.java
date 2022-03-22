package com.dra.login.web;


import com.dra.login.exception.ParamException;
import com.dra.utils.ResponseFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public Object responseError(Exception e){
        if (e.getMessage()==null){
            return new ResponseFormat().getError("未知错误",555);
        }
        return new ResponseFormat().getError(e.getMessage(),555);
    }

    @ExceptionHandler(ParamException.class)
    public Object responseParamError(ParamException e){
        return new ResponseFormat().getError(e.getMessage(),e.getCode());
    }
}
