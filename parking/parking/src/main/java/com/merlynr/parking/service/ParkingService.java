package com.merlynr.parking.service;

import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: ParkingService
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/9
 **/
@Service
public interface ParkingService {
    String add(ParkingLot parkingLot);
    Integer delete(int id);
    List<ParkingLot> findAll();
    PageResult findPage(PageRequest pageRequest);
    Integer update(ParkingLot parkingLot);
}
