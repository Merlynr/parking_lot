package com.merlynr.parking.service.impl;

import com.merlynr.parking.dao.ParkingLotDao;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: ParkingServiceImpl
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/9
 **/
@Component
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private  ParkingLotDao parkingLotDao;

    @Override
    public String add(ParkingLot parkingLot) {
        parkingLotDao.insert(parkingLot);
        return "新增成功";
    }

    @Override
    public String delete(int id) {
        parkingLotDao.deleteByPrimaryKey(id);
        return "删除成功";
    }

    @Override
    public List<ParkingLot> findAll() {
        return parkingLotDao.selectAll();
    }
}
