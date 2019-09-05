package com.miaosha.miaosha.dao;

import com.miaosha.miaosha.entity.SequenceDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceDOMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceDO record);

    int insertSelective(SequenceDO record);

    SequenceDO selectByPrimaryKey(String name);

    /**
     * 根据名称获得 Sequence
     * @param name
     * @return
     */
    SequenceDO getSequenceByName(String name);

    int updateByPrimaryKeySelective(SequenceDO record);

    int updateByPrimaryKey(SequenceDO record);
}