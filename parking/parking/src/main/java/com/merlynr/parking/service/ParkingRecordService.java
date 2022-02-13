package com.merlynr.parking.service;

import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.model.ParkingRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: ParkingRecord
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/9
 **/
@Service
public interface ParkingRecordService {
    String add(ParkingRecord parkingRecord);
    Integer delete(int id);
    List<ParkingRecord> findAll();
    PageResult findPage(PageRequest pageRequest);
    Integer update(ParkingRecord parkingRecord);
    List<ParkingRecord> searchRecordByParkingLot(String parkLot);
}
