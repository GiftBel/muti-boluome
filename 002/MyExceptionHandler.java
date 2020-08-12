package com.cxu.web.controller;

import com.cxu.web.exception.BusinessException;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class )
    public Map<String,Object > handleException(Exception e){
        Map<String,Object> exceptionMap = new HashMap<>();
        exceptionMap.put("code", "访问错误");
        exceptionMap.put("message",e.getMessage());
        return  exceptionMap;
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class )
    public Map<String,Object > handleBusinessException(BusinessException e){
        System.out.printf("异常",e);
        Map<String,Object> exceptionMap = new HashMap<>();
        exceptionMap.put("code", "500");
        exceptionMap.put("message",e.getMessage());
        return  exceptionMap;
    }
}
