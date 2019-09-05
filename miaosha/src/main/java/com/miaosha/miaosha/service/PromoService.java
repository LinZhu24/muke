package com.miaosha.miaosha.service;

import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.model.PromoModel;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/4 11:35
 * @Desc:
 */
public interface PromoService {

    /**
     * 根据商品的ID获取即将进行的或者正在进行的秒杀活动
     *
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
}
