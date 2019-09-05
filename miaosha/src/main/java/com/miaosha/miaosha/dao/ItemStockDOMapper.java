package com.miaosha.miaosha.dao;

import com.miaosha.miaosha.entity.ItemStockDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);

    /**
     * 根据商品ID，查找其库存DO
     * @param itemId
     * @return
     */
    ItemStockDO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    /**
     * 减库存
     * @param itemId
     * @param amount
     * @return
     */
    int decreaseStock(@Param("itemId")Integer itemId, @Param("amount")Integer amount);


}