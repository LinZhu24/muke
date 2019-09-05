package com.miaosha.miaosha.dao;

import com.miaosha.miaosha.entity.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    /**
     * 通过手机号查询用户信息
     * @param telephone
     * @return
     */
    UserInfo selectByTelephone(String telephone);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}