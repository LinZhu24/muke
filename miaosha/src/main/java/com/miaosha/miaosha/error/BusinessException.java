package com.miaosha.miaosha.error;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/7 0:28
 * @Desc: 包装器业务异常类实现
 */
public class BusinessException extends Exception implements CommonErrorInterface {

    private BusinessErrorEnum errorEnum;

    /**
     * 直接接收BusinessErrorEnum枚举的传参用于构造业务异常
     * @param errorEnum
     */
    public BusinessException(BusinessErrorEnum errorEnum){
        super();
        this.errorEnum = errorEnum;
    }

    /**
     * 接收自定义errorMsg的方式构造业务异常
     * @param errorEnum
     * @param errorMsg
     */
    public BusinessException(BusinessErrorEnum errorEnum, String errorMsg){
        super();
        this.errorEnum = errorEnum;
        this.errorEnum.setErrorMsg(errorMsg);
    }

    @Override
    public Integer getErrorCode() {
        return this.errorEnum.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.errorEnum.getErrorMsg();
    }

    @Override
    public CommonErrorInterface setErrorMsg(String errorMsg) {
        this.errorEnum.setErrorMsg(errorMsg);
        return this;
    }
}
