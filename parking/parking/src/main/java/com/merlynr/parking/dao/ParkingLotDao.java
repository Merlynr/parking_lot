package com.merlynr.parking.dao;

import com.merlynr.parking.model.ParkingLot;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParkingLotDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ParkingLot record);

    int insertSelective(ParkingLot record);

    ParkingLot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParkingLot record);

    int updateByPrimaryKey(ParkingLot record);
}