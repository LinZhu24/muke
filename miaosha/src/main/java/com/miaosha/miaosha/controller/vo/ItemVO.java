package com.miaosha.miaosha.controller.vo;

import lombok.Data;
import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/16 9:29
 * @Desc:
 */
@Data
public class ItemVO {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String title;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;


    /**
     * 商品的描述
     */
    private String description;

    /**
     * 销量
     */
    private String sales;

    /**
     * 商品的描述图片的URL
     */
    private String imgUrl;

    /**
     * 记录商品是否在秒杀活动中，以及对应的状态
     * 0：无秒杀活动
     * 1：未开始
     * 2：进行中
     */
    private Integer promoStatus;

    /**
     * 秒杀活动价格
     */
    private BigDecimal promoPrice;

    /**
     * 秒杀活动的 ID
     */
    private Integer promoId;

    /**
     * 秒杀活动的开始时间
     */
    private String startDate;
}
