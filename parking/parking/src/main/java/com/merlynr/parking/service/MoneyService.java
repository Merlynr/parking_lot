package com.merlynr.parking.service;

import com.merlynr.parking.model.Money;
import com.merlynr.parking.model.ParkingRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: MoneyService
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/9
 **/
@Service
public interface MoneyService {
    String add(Money money);
    String delete(int id);
    List<Money> findAll();
}
