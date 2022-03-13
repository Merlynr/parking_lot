package com.merlynr.parking.service;

import com.alibaba.fastjson.JSONObject;
import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.model.ParkingRecord;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
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
    List<ParkingRecord> searchRecordByTimes(String startTime,String endTime,String parkLot) throws ParseException;
    PageResult findLinShiByPage(PageRequest pageRequest);
    String jiaru(JSONObject jsonObject);
    ParkingRecord ddd();
}
