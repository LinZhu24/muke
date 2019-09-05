package com.miaosha.miaosha.error;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/7 0:23
 * @Desc: 错误返回体的 接口
 */
public interface CommonErrorInterface {
    /**
     * 获取错误码
     * @return
     */
    Integer getErrorCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrorMsg();

    /**
     * 自定义错误信息
     * @param errorMsg
     * @return
     */
    CommonErrorInterface setErrorMsg(String errorMsg);
}
