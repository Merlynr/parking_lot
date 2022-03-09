package com.merlynr.parking.service.impl;

import com.merlynr.parking.dao.MoneyRecordDao;
import com.merlynr.parking.model.MoneyRecord;
import com.merlynr.parking.service.MoneyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: MoneyRecordServiceImpl
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/3/7
 **/
@Component
public class MoneyRecordServiceImpl implements MoneyRecordService {
    @Autowired
    private MoneyRecordDao moneyRecordDao;

    @Override
    public String add(MoneyRecord moneyRecord) {
        moneyRecordDao.insertSelective(moneyRecord);
        return "新增成功";
    }

    @Override
    public String delete(int id) {
        moneyRecordDao.deleteByPrimaryKey(id);
        return "删除成功";
    }

    @Override
    public List<MoneyRecord> findAll() {
        moneyRecordDao.selectAll();
        return null;
    }
}
