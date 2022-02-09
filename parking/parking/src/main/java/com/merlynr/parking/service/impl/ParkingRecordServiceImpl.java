package com.merlynr.parking.service.impl;

import com.merlynr.parking.dao.ParkingRecordDao;
import com.merlynr.parking.model.ParkingRecord;
import com.merlynr.parking.service.ParkingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: ParkingRecordServiceImpl
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/9
 **/
@Component
public class ParkingRecordServiceImpl implements ParkingRecordService {

    @Autowired
    private ParkingRecordDao parkingRecordDao;

    @Override
    public String add(ParkingRecord parkingRecord) {
        parkingRecordDao.insert(parkingRecord);
        return "新增成功";
    }

    @Override
    public String delete(int id) {
        parkingRecordDao.deleteByPrimaryKey(id);
        return "删除成功";
    }

    @Override
    public List<ParkingRecord> findAll() {
        return parkingRecordDao.selectAll();
    }
}
