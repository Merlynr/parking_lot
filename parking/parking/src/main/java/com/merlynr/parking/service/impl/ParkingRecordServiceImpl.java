package com.merlynr.parking.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.common.PageUtils;
import com.merlynr.parking.dao.ParkingRecordDao;
import com.merlynr.parking.model.ParkingRecord;
import com.merlynr.parking.service.ParkingRecordService;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
    public Integer delete(int id) {
        parkingRecordDao.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public List<ParkingRecord> findAll() {
        return parkingRecordDao.selectAll();
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest,getPageInfo(pageRequest));
    }

    @Override
    public Integer update(ParkingRecord parkingRecord) {
        return parkingRecordDao.updateByPrimaryKeySelective(parkingRecord);
    }

    @Override
    public List<ParkingRecord> searchRecordByParkingLot(String parkLot) {
        return parkingRecordDao.searchByLicense(parkLot);
    }

    @Override
    public List<ParkingRecord> searchRecordByTimes(String startTime, String endTime, String parkLot) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);
        Timestamp startTimestamp =new Timestamp(start.getTime());
        Timestamp endTimestamp = new Timestamp(end.getTime());
        return parkingRecordDao.searchRecordByTimes(startTimestamp,endTimestamp,parkLot);
    }

    @Override
    public PageResult findLinShiByPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest,getLinShiPageInfo(pageRequest));
    }

    private  PageInfo<ParkingRecord> getLinShiPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ParkingRecord> sysMenus = parkingRecordDao.findLinShiByPage();
        return new PageInfo<ParkingRecord>(sysMenus);
    }

    private PageInfo<ParkingRecord> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<ParkingRecord> sysMenus = parkingRecordDao.selectAll();
        return new PageInfo<ParkingRecord>(sysMenus);
    }
}
