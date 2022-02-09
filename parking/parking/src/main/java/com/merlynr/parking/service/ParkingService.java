package com.merlynr.parking.service;

import com.merlynr.parking.model.ParkingLot;
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
    String delete(int id);
    List<ParkingLot> findAll();
}
