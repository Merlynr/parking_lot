package com.merlynr.parking.controller;

import com.alibaba.fastjson.JSONObject;
import com.merlynr.parking.common.*;
import com.merlynr.parking.model.Money;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.service.MoneyService;
import com.merlynr.parking.service.ParkingService;
import com.merlynr.parking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @className: MoneyController
 * @description: TODO 类描述
 * @author: Merlynr
 * @date: 2022/3/7
 **/
@BaseResponse
@RestController
@RequestMapping("/api/money")
public class MoneyController {
    @Autowired
    private MoneyService moneyService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("add")
    public Object add(@RequestBody Money money){
        JSONObject jsonObject=new JSONObject();
        if (money == null){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            jsonObject.put("msg",moneyService.add(money));
            return jsonObject;
        }
    }

    @UserLoginToken
    @PostMapping("delete")
    public String deleteUser(@RequestBody Map<String,Integer> params){
        int id = params.get("id");
        if (id == 0){
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else{
            return moneyService.delete(id);
        }
    }
    @UserLoginToken
    @PostMapping("update")
    public Integer update(@RequestBody Money money){
        if (money == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return moneyService.update(money);
        }
    }

    @UserLoginToken
    @GetMapping("list")
    public List<Money> list(){
        return moneyService.findAll();
    }

    @UserLoginToken
    @GetMapping("searchByType")
    public  Money searchByType(@RequestParam("type") String type){
        return moneyService.searchByType(type);
    }
}
