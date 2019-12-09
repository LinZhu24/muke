package com.miaosha.miaosha.service.impl;

import com.miaosha.miaosha.dao.TestDOMapper;
import com.miaosha.miaosha.entity.TestDO;
import com.miaosha.miaosha.service.TestService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: LinXueRui
 * @Date: 2019/12/6 18:42
 * @Desc:
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDOMapper testDOMapper;


    @Override
    public List<TestDO> selectByTime(String createTime) {
        List<TestDO> testDO = testDOMapper.selectByTime(createTime);
        return testDO;
    }

    public static void main(String[] args) {
        String a = "2019-12-05T16:00:00.000+0000";
        Date b = DateUtil.parse(a);
        System.out.println(b);
    }
}
