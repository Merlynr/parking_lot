package com.merlynr.parking.controller;

import com.alibaba.fastjson.JSONObject;
import com.merlynr.parking.common.*;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.model.Users;
import com.merlynr.parking.service.ParkingService;
import com.merlynr.parking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @className: CheWeiController
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/2/10
 **/

@BaseResponse
@RestController
@RequestMapping("/api/yuezhu")
public class CheWeiController {
    @Autowired
    private ParkingService parkingService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("get")
    public  List<ParkingLot> get(){
        return parkingService.getList();
    }

    @PostMapping("add")
    public Object add(@RequestBody ParkingLot parkingLot){
        JSONObject jsonObject=new JSONObject();
        if (parkingLot == null){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            jsonObject.put("msg",parkingService.add(parkingLot));
            return jsonObject;
        }
    }

    @UserLoginToken
    @PostMapping("findByPage")
    public Object findPage(@RequestBody PageRequest pageRequest){
        if (pageRequest == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return parkingService.findPage(pageRequest);
        }
    }
    @UserLoginToken
    @PostMapping("delete")
    public Integer deleteUser(@RequestBody Map<String,Integer> params){
        int id = params.get("id");
        if (id == 0){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            return parkingService.delete(id);
        }
    }
    @UserLoginToken
    @PostMapping("update")
    public Integer update(@RequestBody ParkingLot parkingLot){
        if (parkingLot == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return parkingService.update(parkingLot);
        }
    }

    @UserLoginToken
    @GetMapping("list")
    public List<ParkingLot> list(){
        return parkingService.findAll();
    }
}
