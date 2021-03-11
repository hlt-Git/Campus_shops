package com.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: hlt
 * @Description: 统一异常处理类
 * @Date: 2019/12/27 16:09
 */

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String error(Exception e){
        e.printStackTrace();
        return "/error/500";
    }
}
