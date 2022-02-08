package com.merlynr.parking.dao;

import com.merlynr.parking.model.Money;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MoneyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Money record);

    int insertSelective(Money record);

    Money selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Money record);

    int updateByPrimaryKey(Money record);
}