package com.miaosha.miaosha.service;

import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.model.UserInfoModel;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/6 19:34
 * @Desc:
 */
public interface UserService {

    /**
     * 根据用户ID获取用户信息Model模型
     * @param id
     * @return
     */
    UserInfoModel getUserById(Integer id);

    /**
     * 注册
     * @param model
     * @throws BusinessException
     */
    void register(UserInfoModel model) throws BusinessException;


    /**
     * encryptPassword是加密后的密码
     * @param telephone
     * @param encryptPassword
     * @return
     * @throws BusinessException
     */
    UserInfoModel validateLogin(String telephone,String encryptPassword) throws BusinessException;
}
