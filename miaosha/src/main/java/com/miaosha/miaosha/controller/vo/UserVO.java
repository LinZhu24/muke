package com.miaosha.miaosha.controller.vo;

import lombok.Data;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/6 19:45
 * @Desc: 展示在前端的用户VO
 */
@Data
public class UserVO {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telephone;
}
