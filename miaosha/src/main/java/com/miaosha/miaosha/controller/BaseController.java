package com.miaosha.miaosha.controller;

import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.util.response.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/5 15:39
 * @Desc:
 */
public class BaseController {
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    /**
     * 定义ExceptionHandler来处理controller不能吸收的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> dataMap = new HashMap<>(2);
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            dataMap.put("errorCode",businessException.getErrorCode());
            dataMap.put("errorMsg",businessException.getErrorMsg());
        }else {
            dataMap.put("errorCode", BusinessErrorEnum.UNKNOWN_ERROR.getErrorCode());
            dataMap.put("errorMsg", BusinessErrorEnum.UNKNOWN_ERROR.getErrorMsg());
        }
        return ResponseEntity.create(dataMap,"fail");
    }

}
