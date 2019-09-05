package com.miaosha.miaosha.dao;

import com.miaosha.miaosha.entity.PromoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Integer id);

    /**
     * 根据商品ID查找秒杀活动
     * @param itemId
     * @return
     */
    PromoDO selectByItemId(@Param("itemId") Integer itemId);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);


}