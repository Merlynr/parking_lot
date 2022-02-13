package com.merlynr.parking.dao;

import com.merlynr.parking.model.ParkingRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ParkingRecord record);

    int insertSelective(ParkingRecord record);

    ParkingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParkingRecord record);

    int updateByPrimaryKey(ParkingRecord record);
    List<ParkingRecord> selectAll();

    List<ParkingRecord> searchByLicense(String license);
}