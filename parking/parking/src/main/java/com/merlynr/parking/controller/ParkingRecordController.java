package com.merlynr.parking.controller;

import com.alibaba.fastjson.JSONObject;
import com.merlynr.parking.common.*;
import com.merlynr.parking.model.ParkingLot;
import com.merlynr.parking.model.ParkingRecord;
import com.merlynr.parking.service.ParkingRecordService;
import com.merlynr.parking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @UserLoginToken
    @GetMapping("searchByTimes")
    public List<ParkingRecord> searchByTimes(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime, @RequestParam("licensePlates")  String licensePlates) throws ParseException {
        return parkingRecordService.searchRecordByTimes(startTime,endTime,licensePlates);
    }

    /**
     * 获取临时车缴费记录
     * @param pageRequest
     * @return
     */
    @UserLoginToken
    @PostMapping("findLinShiByPage")
    public Object findLinShiByPage(@RequestBody PageRequest pageRequest){
        if (pageRequest == null) {
            throw new BaseException(ResponseCode.RESOURCES_NOT_EXIST);
        }else {
            return parkingRecordService.findLinShiByPage(pageRequest);
        }
    }

    @GetMapping("get")
    public  ParkingRecord get(){
        System.out.println(parkingRecordService.ddd());
        return parkingRecordService.ddd();
    }

    @RequestMapping(value = "jiaru",method = RequestMethod.POST)
    public String jiaru(HttpServletRequest request){
//        System.out.println(request.toString());
//        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
//        List<MultipartFile> files= ((MultipartHttpServletRequest) request).getFiles("file");
//        String license = multipartRequest.getParameter("license");
//        System.out.println(license);
        JSONObject jsonParam = null;
        try {
            System.out.println(request);
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());

            return parkingRecordService.jiaru(jsonParam.getJSONObject("AlarmInfoPlate").getJSONObject("result").getJSONObject("PlateResult"));
        } catch (Exception e) {
//            e.printStackTrace();
            return "等待。。。";
        }
    }


}
