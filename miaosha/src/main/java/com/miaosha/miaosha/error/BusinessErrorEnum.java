package com.miaosha.miaosha.error;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/7 0:23
 * @Desc: 错误信息枚举
 */
public enum BusinessErrorEnum implements CommonErrorInterface {
    /**
     * 通用参数错误枚举
     */
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),

    /**
     * 通用的未知错误返回枚举
     */
    UNKNOWN_ERROR(10002,"未知错误"),

    /**
     * 20000开头代表用户信息业务发生相关错误
     */
    USER_NOT_EXIST(20001,"用户信息不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登录"),

    /**
     * 30000开头代表交易信息业务发生相关的错误
     */
    GOOD_NOT_EXIST(30001,"商品信息不存在"),
    STOCK_NOT_ENOUGH(30002,"商品库存不足"),
    GOOD_DOWN_SALE(30003,"商品已下架"),
    GOOD_DOWN_S(30004,"商品已下架"),

    /**
     * 40000开头代表秒杀活动业务发生相关的错误
     */
    GOOD_NO_PROMO(40001,"商品无秒杀活动");
    private Integer errorCode;
    private String errorMsg;

    BusinessErrorEnum(Integer errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonErrorInterface setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }}
