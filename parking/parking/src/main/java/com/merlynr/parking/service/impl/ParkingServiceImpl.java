package com.merlynr.parking.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.common.PageUtils;
import com.merlynr.parking.dao.ParkingLotDao;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.model.Users;
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
    public Integer delete(int id) {
        parkingLotDao.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public List<ParkingLot> findAll() {
        return parkingLotDao.selectAll();
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest,getPageInfo(pageRequest));
    }

    @Override
    public Integer update(ParkingLot parkingLot) {
        return parkingLotDao.updateByPrimaryKeySelective(parkingLot);
    }

    private PageInfo<ParkingLot> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ParkingLot> sysMenus = parkingLotDao.selectAll();
        return new PageInfo<ParkingLot>(sysMenus);
    }
}
