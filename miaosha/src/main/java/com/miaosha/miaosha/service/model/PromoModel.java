package com.miaosha.miaosha.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/4 11:17
 * @Desc: 秒杀信息模型
 */
@Data
public class PromoModel {
    /**
     * 秒杀活动的主键ID
     */
    private Integer id;

    /**
     * 秒杀的状态 ：1:未开始，2:进行中，3:已结束
     */
    private Integer status;

    /**
     * 秒杀信息的描述
     */
    private String promoName;

    /**
     * 商品ID
     */
    private Integer itemId;

    /**
     * 秒杀时的折扣价格
     */
    private BigDecimal promoItemPrice;

    /**
     * 秒杀时商品的库存
     */
    private Integer promoAmount;

    /**
     * 秒杀活动的开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动的结束时间
     */
    private DateTime endDate;
}
