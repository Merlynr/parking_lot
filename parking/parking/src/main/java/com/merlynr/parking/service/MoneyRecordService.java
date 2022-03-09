package com.merlynr.parking.service;

import com.merlynr.parking.model.MoneyRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: moneyRecordService
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/3/7
 **/
@Service
public interface MoneyRecordService {
    String add(MoneyRecord moneyRecord);
    String delete(int id);
    List<MoneyRecord> findAll();
}
