package com.miaosha.miaosha.util.response;

import lombok.Data;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/6 23:43
 * @Desc: RestFul风格的统一的返回实体类
 */
@Data
public class ResponseEntity {
    /**
     * 若 status == success  则data内返回前端需要的json数据，
     * 若 status == fail，   则data内返回通用的错误码格式
     */
    private String status;
    private Object data;

    /**
     * 定义一个通用的创建方法(默认 status 为成功)
     *
     * @param result
     * @return
     */
    public static ResponseEntity create(Object result) {
        return ResponseEntity.create(result, "success");
    }


    /**
     * 创建 响应体
     * @param result
     * @param status
     * @return
     */
    public static ResponseEntity create(Object result, String status) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setStatus(status);
        responseEntity.setData(result);
        return responseEntity;
    }

}
