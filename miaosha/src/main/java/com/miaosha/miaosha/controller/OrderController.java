package com.miaosha.miaosha.controller;

import com.miaosha.miaosha.controller.vo.ItemVO;
import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.ItemService;
import com.miaosha.miaosha.service.OrderService;
import com.miaosha.miaosha.service.model.ItemModel;
import com.miaosha.miaosha.service.model.OrderModel;
import com.miaosha.miaosha.service.model.UserInfoModel;
import com.miaosha.miaosha.util.response.ResponseEntity;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 21:50
 * @Desc:
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController {
    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * 创建订单
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/createOrder",method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public ResponseEntity createOrder(Integer itemId , Integer amount,
                                      @RequestParam(name = "promoId",required = false)Integer promoId)throws BusinessException {
        //获取用户的登录信息
        Boolean isLogin = (Boolean) this.httpServletRequest.getSession().getAttribute("isLogin");
        if (isLogin == null || !isLogin) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_LOGIN);
        }
        UserInfoModel userModel = (UserInfoModel)this.httpServletRequest.getSession().getAttribute("loginUser");
        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId,amount);
        return ResponseEntity.create(orderModel);
    }
}
