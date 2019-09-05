package com.miaosha.miaosha.controller;

import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.PromoService;
import com.miaosha.miaosha.util.response.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/4 14:21
 * @Desc:
 */
@Controller
@RequestMapping("/promo")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class PromoController {

    @Autowired
    PromoService promoService;

    @RequestMapping("/getPromo")
    @ResponseBody
    public ResponseEntity getPromo(Integer itemId) throws BusinessException {
        promoService.getPromoByItemId(itemId);

        return ResponseEntity.create(null);
    }

}
