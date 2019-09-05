package com.miaosha.miaosha.dao;

import com.miaosha.miaosha.entity.ItemDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    /**
     * 获取商品列表 list
     * @return
     */
    List<ItemDO> listItem();

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    /**
     * 增加销量
     * @param id
     * @param amount
     * @return
     */
    int increaseSales(@Param("id") Integer id,@Param("amount") Integer amount);
}