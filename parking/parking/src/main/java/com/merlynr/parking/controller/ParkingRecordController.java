package com.merlynr.parking.controller;

import com.alibaba.fastjson.JSONObject;
import com.merlynr.parking.common.*;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.model.ParkingRecord;
import com.merlynr.parking.service.ParkingRecordService;
import com.merlynr.parking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @className: ParkingRecordController
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/13
 **/

@BaseResponse
@RestController
@RequestMapping("/api/parking")
public class ParkingRecordController {

    @Autowired
    private ParkingRecordService parkingRecordService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("add")
    public Object add(@RequestBody ParkingRecord parkingRecord){
        JSONObject jsonObject=new JSONObject();
        if (parkingRecord == null){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            jsonObject.put("msg",parkingRecordService.add(parkingRecord));
            return jsonObject;
        }
    }

    @UserLoginToken
    @PostMapping("findByPage")
    public Object findPage(@RequestBody PageRequest pageRequest){
        if (pageRequest == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return parkingRecordService.findPage(pageRequest);
        }
    }
    @UserLoginToken
    @PostMapping("delete")
    public Integer deleteUser(@RequestBody Map<String,Integer> params){
        int id = params.get("id");
        if (id == 0){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            return parkingRecordService.delete(id);
        }
    }
    @UserLoginToken
    @PostMapping("update")
    public Integer update(@RequestBody ParkingRecord parkingRecord){
        if (parkingRecord == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return parkingRecordService.update(parkingRecord);
        }
    }

    @UserLoginToken
    @GetMapping("list")
    public List<ParkingRecord> list(){
        return parkingRecordService.findAll();
    }

    @UserLoginToken
    @GetMapping("searchByLicense")
    public List<ParkingRecord> searchByLicense(@RequestParam("licensePlates")  String licensePlates){
        if (licensePlates == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return parkingRecordService.searchRecordByParkingLot(licensePlates);
        }
    }
}
