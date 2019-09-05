package com.miaosha.miaosha.service;

import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.model.OrderModel;
import com.miaosha.miaosha.util.response.ResponseEntity;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/2 15:49
 * @Desc:
 */
public interface OrderService {

    /**
     * 创建订单，两种方式：推荐使用第一种，第二种对于效率稍有影响
     * 1:通过前端url传来的秒杀活动的ID，然后把下单接口内校验对应ID是否属于对应商品且活动已开始
     * 2：直接在下单接口内判断该商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
     * @param userId
     * @param itemId
     * @param promoId
     * @param amount
     * @return
     * @throws BusinessException
     */
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId,Integer amount) throws BusinessException;
}
