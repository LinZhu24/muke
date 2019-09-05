package com.miaosha.miaosha.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/2 10:04
 * @Desc:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/demo1")
    public String demo1(){
        return "fuck";
    }
}
