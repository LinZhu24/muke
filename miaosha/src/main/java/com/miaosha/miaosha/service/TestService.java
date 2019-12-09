package com.miaosha.miaosha.service;

import com.miaosha.miaosha.entity.TestDO;

import java.util.List;

/**
 * @Author: LinXueRui
 * @Date: 2019/12/6 18:40
 * @Desc:
 */
public interface TestService {
    /**
     * selectByTime
     * @param createTime
     * @return
     */
    List<TestDO> selectByTime(String createTime);
}
