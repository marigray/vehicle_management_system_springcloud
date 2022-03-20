package com.dra.msg.web;

import com.dra.msg.exception.ParamException;
import com.dra.pojo.msg.FormatData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public Object responseError(Exception e){
        if (e.getMessage()==null){
            return new FormatData<>(null,555,"未知错误");
        }
        return new FormatData<>(null,544,e.getMessage());
    }

    @ExceptionHandler(ParamException.class)
    public Object responseParamError(ParamException e){
        return new FormatData<>(null,e.getCode(),e.getMessage());
    }
}
