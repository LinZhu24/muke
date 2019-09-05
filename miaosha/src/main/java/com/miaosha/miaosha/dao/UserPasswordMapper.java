package com.miaosha.miaosha.dao;

import com.miaosha.miaosha.entity.UserPassword;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassword record);

    int insertSelective(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);

    /**
     * 根据用户ID获取用户密码信息
     * @param userId
     * @return
     */
    UserPassword selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPassword record);

    int updateByPrimaryKey(UserPassword record);
}