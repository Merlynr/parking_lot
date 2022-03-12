package com.merlynr.parking.dao;

import com.merlynr.parking.model.ParkingLot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ParkingLotDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ParkingLot record);

    int insertSelective(ParkingLot record);

    ParkingLot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParkingLot record);

    int updateByPrimaryKey(ParkingLot record);

    List<ParkingLot> selectAll();

    List<ParkingLot> findUnUsedFromUser();

    List<ParkingLot> findUnUsedFromTemp();
}