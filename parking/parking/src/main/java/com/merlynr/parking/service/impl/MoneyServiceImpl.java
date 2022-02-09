package com.merlynr.parking.service.impl;

import com.merlynr.parking.dao.MoneyDao;
import com.merlynr.parking.model.Money;
import com.merlynr.parking.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: MoneyServiceImpl
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/9
 **/
@Component
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private MoneyDao moneyDao;
    @Override
    public String add(Money money) {
        moneyDao.insert(money);
        return "新增成功";
    }

    @Override
    public String delete(int id) {
        moneyDao.deleteByPrimaryKey(id);
        return "删除成功";
    }

    @Override
    public List<Money> findAll() {
        return moneyDao.selectAll();
    }
}
