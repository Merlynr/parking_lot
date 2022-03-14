package com.merlynr.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.merlynr.parking.common.PageRequest;
import com.merlynr.parking.common.PageResult;
import com.merlynr.parking.common.PageUtils;
import com.merlynr.parking.dao.ParkingLotDao;
import com.merlynr.parking.dao.ParkingRecordDao;
import com.merlynr.parking.dao.UsersDao;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.model.ParkingRecord;
import com.merlynr.parking.model.Users;
import com.merlynr.parking.service.ParkingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private ParkingLotDao parkingLotDao;

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

    @Override
    public String jiaru(JSONObject plateRes) {
        ParkingRecord parkingRecord = new ParkingRecord();
        ParkingRecord pr = parkingRecordDao.searchChuku((String) plateRes.get("license"));
        if(pr!=null){
            pr.setEndTime(new Date());
            pr.setCarOut("data:image/jpeg;base64,"+(String) plateRes.get("imageFragmentFile"));
            parkingRecordDao.updateByPrimaryKeySelective(pr);
            ParkingLot pl = parkingLotDao.selectByPrimaryKey(Integer.parseInt(pr.getParkingLot()));
            pl.setUsed("0");
            parkingLotDao.updateByPrimaryKey(pl);
            return "出库";
        }else{
            System.out.println("BB");
            parkingRecord.setLicense((String) plateRes.get("license"));
            parkingRecord.setStartTime(new Date());
            Users user =  usersDao.searchByLicense((String) plateRes.get("license"));
            if (user == null) {
//            temp
                ParkingLot pl=parkingLotDao.findUnUsedFromTemp().get(0);
                parkingRecord.setParkingLot(pl.getId().toString());
                pl.setUsed("1");
                parkingLotDao.updateByPrimaryKey(pl);
            }else{
                parkingRecord.setUser(user.getName());
                ParkingLot pl=parkingLotDao.findUnUsedFromUser().get(0);
                parkingRecord.setParkingLot(pl.getId().toString());
                pl.setUsed("1");
                parkingLotDao.updateByPrimaryKey(pl);
            }
            parkingRecord.setCarIn("data:image/jpeg;base64,"+(String) plateRes.get("imageFragmentFile"));
            parkingRecordDao.insert(parkingRecord);
            return "进入";
        }
    }

    @Override
    public ParkingRecord ddd() {
        return parkingRecordDao.searchChuku("京AF0236");
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
