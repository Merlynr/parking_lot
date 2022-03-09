package com.merlynr.parking.dao;

import com.merlynr.parking.model.MoneyRecord;
import com.merlynr.parking.model.ParkingLot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MoneyRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyRecord record);

    int insertSelective(MoneyRecord record);

    MoneyRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyRecord record);

    int updateByPrimaryKey(MoneyRecord record);

    List<MoneyRecord> selectAll();
}