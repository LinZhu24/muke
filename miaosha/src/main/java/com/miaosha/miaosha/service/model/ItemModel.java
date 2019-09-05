package com.miaosha.miaosha.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 20:53
 * @Desc: 商品model
 * 商品的必备元素有哪些呢
 */
@Data
public class ItemModel {
    private Integer id;
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String title;

    /**
     * 价格
     */
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格不能小于0")
    private BigDecimal price;

    /**
     * 库存
     */
    @NotNull(message = "库存不能不填")
    private Integer stock;


    /**
     * 商品的描述
     */
    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    /**
     * 销量
     */
    private String sales;

    /**
     * 商品的描述图片的URL
     */
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;

    /**
     * 使用聚合模型的形式,若promoModel不为空，则表示其拥有还未结束的秒杀活动
     */
    private PromoModel promoModel;
}
