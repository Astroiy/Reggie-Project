package com.itheima.reggie.handler;

import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.ResultInfo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultInfo handlerDuplicateKeyException(Exception e){
        e.printStackTrace();

        if(e.getMessage().contains("idx_category_name")){
            return ResultInfo.error("分类名称的值重复");
        }

        return ResultInfo.error("填写的值重复");
    }

    @ExceptionHandler(CustomException.class)
    public ResultInfo handlerCustomException(Exception e){
        e.printStackTrace();

        return ResultInfo.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo handlerException(Exception e){
        e.printStackTrace();

        return ResultInfo.error("服务器开小差了，请联系管理员");

    }
}
