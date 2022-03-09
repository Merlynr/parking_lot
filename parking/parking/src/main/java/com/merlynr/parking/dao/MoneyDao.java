package com.merlynr.parking.dao;

import com.merlynr.parking.model.Money;
import com.merlynr.parking.model.ParkingLot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoneyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Money record);

    int insertSelective(Money record);

    Money selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Money record);

    int updateByPrimaryKey(Money record);

    List<Money> selectAll();

    Money selectByType(String type);

}