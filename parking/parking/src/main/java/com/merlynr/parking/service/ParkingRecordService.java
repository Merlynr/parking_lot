package com.merlynr.parking.service;

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
    String delete(int id);
    List<ParkingRecord> findAll();
}
