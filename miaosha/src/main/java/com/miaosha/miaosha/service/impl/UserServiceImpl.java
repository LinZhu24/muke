package com.miaosha.miaosha.service.impl;

import com.miaosha.miaosha.dao.UserInfoMapper;
import com.miaosha.miaosha.dao.UserPasswordMapper;
import com.miaosha.miaosha.entity.UserInfo;
import com.miaosha.miaosha.entity.UserPassword;
import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.UserService;
import com.miaosha.miaosha.service.model.UserInfoModel;
import com.miaosha.miaosha.validator.ValidationResult;
import com.miaosha.miaosha.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/6 19:35
 * @Desc:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Autowired
    private ValidatorImpl validator;


    @Override
    public UserInfoModel getUserById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        if (userInfo == null){
            return null;
        }
        UserPassword userPassword = userPasswordMapper.selectByUserId(id);
        return convertFromDO(userInfo,userPassword);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void register(UserInfoModel model) throws BusinessException {
        if (model == null){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"对象为空");
        }
//        老模式参数校验
//        if (StringUtils.isEmpty(model.getName()) || model.getGender() == null || model.getAge() == null || StringUtils.isEmpty(model.getTelephone())){
//            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"入参不合法");
//        }

        //新模式
        ValidationResult validationResult = validator.validate(model);
        if (validationResult.isHasErrors()){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,validationResult.getErrorMsg());
        }
        UserInfo userInfo = convertFromModel(model);
        try{
            userInfoMapper.insertSelective(userInfo);
        } catch (DuplicateKeyException ex){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"手机号已存在，请勿重复注册");
        }
        model.setId(userInfo.getId());
        UserPassword userPassword = convertPasswordFromModel(model);
        userPasswordMapper.insertSelective(userPassword);
    }

    @Override
    public UserInfoModel validateLogin(String telephone, String encryptPassword) throws BusinessException {
        //通过手机号得到用户的信息
        UserInfo userInfo = userInfoMapper.selectByTelephone(telephone);
        if (userInfo == null){
            throw new BusinessException(BusinessErrorEnum.USER_LOGIN_FAIL);
        }
        UserPassword userPassword = userPasswordMapper.selectByUserId(userInfo.getId());
        UserInfoModel model = convertFromDO(userInfo,userPassword);
        //对比用户信息内加密的密码与传输进来的密码是否相同
        if (!StringUtils.equals(encryptPassword,model.getEncrpyPassword())){
            throw new BusinessException(BusinessErrorEnum.USER_LOGIN_FAIL);
        }
        return model;
    }

    /**
     * //实现model--->dataObject的方法
     * @param userInfoModel
     * @return
     */
    private UserPassword convertPasswordFromModel(UserInfoModel userInfoModel){
        if (userInfoModel == null){
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setEncrpyPassword(userInfoModel.getEncrpyPassword());
        userPassword.setUserId(userInfoModel.getId());
        return userPassword;
    }

    /**
     * //实现model--->dataObject的方法
     * @param userInfoModel
     * @return
     */
    private UserInfo convertFromModel(UserInfoModel userInfoModel){
        if (userInfoModel == null){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoModel,userInfo);
        return userInfo;
    }

    /**
     * 把数据库表对象转化为数据Model对象
     * @param userInfo
     * @param userPassword
     * @return
     */
    public UserInfoModel convertFromDO(UserInfo userInfo, UserPassword userPassword){
        UserInfoModel userInfoModel = new UserInfoModel();
        if (userInfo == null){
            return null;
        }
        BeanUtils.copyProperties(userInfo,userInfoModel);
        if (userPassword != null){
            userInfoModel.setEncrpyPassword(userPassword.getEncrpyPassword());
        }
        return userInfoModel;
    }
}
