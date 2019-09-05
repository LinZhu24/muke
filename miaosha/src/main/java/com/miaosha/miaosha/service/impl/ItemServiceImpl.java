package com.miaosha.miaosha.service.impl;

import com.miaosha.miaosha.dao.ItemDOMapper;
import com.miaosha.miaosha.dao.ItemStockDOMapper;
import com.miaosha.miaosha.entity.ItemDO;
import com.miaosha.miaosha.entity.ItemStockDO;
import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.ItemService;
import com.miaosha.miaosha.service.PromoService;
import com.miaosha.miaosha.service.model.ItemModel;
import com.miaosha.miaosha.service.model.PromoModel;
import com.miaosha.miaosha.validator.ValidationResult;
import com.miaosha.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 21:23
 * @Desc:
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //1.校验入参
        ValidationResult validate = validator.validate(itemModel);
        if (validate.isHasErrors()){
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR,validate.getErrorMsg());
        }
        //2.把 model ---> dataObject
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);
        //3.写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //4.返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    /**
     * model---> dataObject
     * @param itemModel
     * @return
     */
    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    /**
     * model---> dataObject
     * @param itemModel
     * @return
     */
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        List<ItemModel> modelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            return convertModelFromDataObject(itemDO, itemStockDO);
        }).collect(Collectors.toList());
        return modelList;
    }

    /**
     * 根据 itemId 获得 ItemModel
     * @param itemId
     * @return
     */
    @Override
    public ItemModel getItemById(Integer itemId) {
        //根据主键查询获得itemDO对象
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);
        if (itemDO == null){
            return null;
        }
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemId);
        //把 dataObject  -->  model
        ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
        //获取商品的活动信息
        PromoModel promoModel = promoService.getPromoByItemId(itemId);
        if (promoModel != null && promoModel.getStatus()!= 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            //更新库存成功
            return true;
        } else {
            //更新库存失败
            return false;
        }
    }

    /**
     * 增加销量
     * @param itemId
     * @param amount
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseSales(itemId,amount);
    }

    /**
     * 更新商品的状态
     * @param itemModel
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateItem(ItemModel itemModel) {
        ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
        //更新库存
        itemStockDOMapper.updateByPrimaryKeySelective(itemStockDO);
    }

    /**
     * 根据  ItemDO 和 ItemStockDo -----> ItemModel
     * @param itemDO
     * @param itemStockDO
     * @return
     */
    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        if (itemDO ==null || itemStockDO == null){
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
