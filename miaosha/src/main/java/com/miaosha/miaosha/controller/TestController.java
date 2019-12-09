package com.miaosha.miaosha.controller;

import com.miaosha.miaosha.entity.TestDO;
import com.miaosha.miaosha.service.TestService;
import com.miaosha.miaosha.util.response.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/2 10:04
 * @Desc:
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private TestService testService;


    @RequestMapping("/demo1")
    public String demo1(){
        return "fuck";
    }


    @RequestMapping("/demo2")
    public List<TestDO> demo2(){
        List<TestDO> testDO = testService.selectByTime("2019-12-06");
        return testDO;
    }
}
