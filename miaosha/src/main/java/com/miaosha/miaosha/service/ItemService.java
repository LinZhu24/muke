package com.miaosha.miaosha.service;

import com.miaosha.miaosha.error.BusinessException;
import com.miaosha.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 21:20
 * @Desc:
 */
public interface ItemService {

    /**
     * 创建商品
     *
     * @param itemModel
     * @return
     * @throws BusinessException
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     *
     * @return
     */
    List<ItemModel> listItem();

    /**
     * 商品详情浏览
     *
     * @param itemId
     * @return
     */
    ItemModel getItemById(Integer itemId);

    /**
     * 商品减库存操作
     *
     * @param itemId
     * @param amount
     * @return
     */
    boolean decreaseStock(Integer itemId, Integer amount);

    /**
     * 商品销量增加
     *
     * @param itemId
     * @param amount
     * @return
     */
    void increaseSales(Integer itemId, Integer amount);

    /**
     * 更新商品的状态
     *
     * @param itemModel
     */
    void updateItem(ItemModel itemModel);
}
