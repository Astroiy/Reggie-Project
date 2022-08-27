package com.itheima.reggie.handler;

import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResultInfo exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return ResultInfo.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo exceptionHandler(Exception ex){
        log.error(ex.getMessage());
        return ResultInfo.error("对不起，网络问题，请稍后再试");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultInfo duplicateKeyExceptionHandler(Exception ex){
        log.error(ex.getMessage());
        return ResultInfo.error("名称重复");
    }
}
