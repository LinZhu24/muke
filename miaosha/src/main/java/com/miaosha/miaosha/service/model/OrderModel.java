package com.miaosha.miaosha.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/2 15:35
 * @Desc: 用户下单的交易模型
 */
@Data
public class OrderModel {
    /**
     * 交易ID   eg.  20190101123356200123388（各个电商都有自己的规定）
     */
    private String id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 购买的商品ID
     */
    private Integer itemId;


    /**
     * 若非空，则表示以秒杀商品方式下单
     */
    private Integer promoId;


    /**
     * 购买的商品的单价，若promoId非空，则价格为秒杀价格
     */
    private BigDecimal itemPrice;

    /**
     * 购买的数量
     */
    private Integer amount;

    /**
     * 购买的总金额，若promoId非空，则价格为秒杀价格
     */
    private BigDecimal orderPrice;

}
