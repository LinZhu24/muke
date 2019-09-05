package com.miaosha.miaosha.service.impl;

import com.miaosha.miaosha.dao.PromoDOMapper;
import com.miaosha.miaosha.entity.PromoDO;
import com.miaosha.miaosha.service.PromoService;
import com.miaosha.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/4 11:37
 * @Desc:
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    /**
     * 根据商品的ID查询该商品是否有秒杀活动
     *
     * @param itemId
     * @return
     */
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promo = promoDOMapper.selectByItemId(itemId);
        if (promo == null) {
            return null;
        }
        PromoModel promoModel = convertModelFromDO(promo);
        //判断当前时间，商品是否即将秒杀还是正在秒杀
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        }else if (promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertModelFromDO(PromoDO promo){
        if (promo == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promo,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promo.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promo.getStartDate()));
        promoModel.setEndDate(new DateTime(promo.getEndDate()));
        return promoModel;
    }
}
