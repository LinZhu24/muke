package com.miaosha.miaosha.service.impl;

import com.miaosha.miaosha.dao.OrderDOMapper;
import com.miaosha.miaosha.dao.SequenceDOMapper;
import com.miaosha.miaosha.entity.OrderDO;
import com.miaosha.miaosha.entity.SequenceDO;
import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.ItemService;
import com.miaosha.miaosha.service.OrderService;
import com.miaosha.miaosha.service.UserService;
import com.miaosha.miaosha.service.model.ItemModel;
import com.miaosha.miaosha.service.model.OrderModel;
import com.miaosha.miaosha.service.model.UserInfoModel;
import com.miaosha.miaosha.util.response.ResponseEntity;
import com.miaosha.miaosha.validator.ValidatorImpl;
import com.sun.tools.internal.xjc.reader.dtd.bindinfo.BIUserConversion;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: LinXueRui
 * @Date: 2019/9/2 15:51
 * @Desc:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId,Integer amount) throws BusinessException {
        // 1.先进行校验
        // 校验用户是否合法，
        // 商品是否存在，
        // 购买数量是否合法
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null){
            throw new BusinessException(BusinessErrorEnum.GOOD_NOT_EXIST);
        }
        Integer stock = itemModel.getStock();
        if (stock == 0){
            throw new BusinessException(BusinessErrorEnum.STOCK_NOT_ENOUGH);
        }
        UserInfoModel user = userService.getUserById(userId);
        if (user == null){
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        if (amount > stock || amount <= 0 || amount > 99){
            //关于大于99这一判断，是因为某些商品不能买太多，必须进行限制
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"输入的购买数量不合法");
        }
        //校验秒杀活动信息
        if (promoId != null) {
            //(1)校验对应的秒杀活动是否对应当前的商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
            //(2)校验活动是否正在进行中
            if (itemModel.getPromoModel().getStatus() != 2) {
                throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,"活动还未开始");
            }
        }
        //2落单减库存   与  （支付减库存）
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(BusinessErrorEnum.STOCK_NOT_ENOUGH);
        }
        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        //平常价
        BigDecimal normalPrice = itemModel.getPrice();
        if (promoId != null) {
            //若promoId不为空，则以秒杀价格下单
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            //若promoId为空，则以平常价格下单
            orderModel.setItemPrice(normalPrice);
        }
        BigDecimal amountBig = new BigDecimal(amount);
        BigDecimal totalPrice = amountBig.multiply(orderModel.getItemPrice());
        orderModel.setOrderPrice(totalPrice);
        orderModel.setPromoId(promoId);
        //生成交易流水号（订单号），该ID的设计根据公司不同，设计不同
        orderModel.setId(generateOrderNumber());
        OrderDO orderDO = convertOrderDOFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //销量增加
        itemService.increaseSales(itemId,amount);
        //4.返回给前端
        return orderModel;
    }

    /**
     * 生成订单号
     * 在单服务器的情况下，
     * todo:若是集群环境下，这个全局sequence如何来保证唯一呢，或者订单号如何来保证唯一呢
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNumber(){
        StringBuilder builder = new StringBuilder();
        //订单号一共有22位
        //前14位为年月日时分秒，如20190901123511
        //为了保证能够直观的分类，按照天的维度或者是月的维度
        builder.append(DateUtil.format(new Date(),"yyyyMMddHHmmss"));

        //中间6位为自增序列，6位是为了保证订单号不重复，若一天的 订单量超过6位数的话，则需要进行调整
        //SequenceDO 是为了保证得到不重复的自增序列数   step为步长   currentValue为当前值   name为业务名称
        SequenceDO sequenceDo = sequenceDOMapper.getSequenceByName("order_info");
        int sequenceValue = sequenceDo.getCurrentValue();
        sequenceDo.setCurrentValue(sequenceValue + sequenceDo.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDo);
        //todo:——————问题1:当currentValue的值超过999999的时候，这6位自增序列就会被破坏了，所以需要在
        //todo:——————设计表结构的时候加一个默认最大值，当currentValue+step超过最大值后，我们将currentValue置为0，循环使用
        //todo:——————问题2：由于生成订单号的方法被包裹在事务里面，所以在遇到错误后。会回滚，currentValue的值就不会变化，但是作为
        //todo:——————电商系统我们希望这个currentValue无论如何都一直自增下去，保证一个全局唯一性。

        /**
         *
         * 研究一下Transactional里面的Propagation属性
         * REQUIRES_NEW：代表开启新的事务，并且在执行完后提交掉
         */

        String sequenceStr = String.valueOf(sequenceValue);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            builder.append(0);
        }
        builder.append(sequenceStr);

        //后2位为分库分表位（订单表的水平拆分）一般是00-99
        //为了分担订单压力，
        //比如userId=10023，那么10023%100的结果（只是一种计算方式而已，肯定还会有更复杂的计算方式），
        //就将该用户的订单分配到专门的库，表中去
        //暂时固定分配到00库表中去
        builder.append("00");
        return builder.toString();
    }
    /**
     * Model 转化为 DO
     * @param model
     * @return
     */
    private OrderDO convertOrderDOFromOrderModel(OrderModel model){
        if (model == null){
            return null;
        }
        OrderDO orderDo = new OrderDO();
        BeanUtils.copyProperties(model,orderDo);
        orderDo.setItemPrice(model.getItemPrice().doubleValue());
        orderDo.setOrderPrice(model.getOrderPrice().doubleValue());
        return orderDo;
    }

}
