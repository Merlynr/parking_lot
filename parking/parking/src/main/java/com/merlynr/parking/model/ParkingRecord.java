package com.merlynr.parking.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * parking_record
 * @author 
 */
@Data
public class ParkingRecord implements Serializable {
    /**
     * 停车记录
     */
    private Integer id;

    /**
     * 用户
     */
    private String user;

    private Date startTime;

    private Date endTime;

    /**
     * 车位
     */
    private String parkingLot;

    /**
     * 入场图片
     */
    private String carIn;

    /**
     * 出场
     */
    private String carOut;

    private String money;

    /**
     * 缴费时间
     */
    private String endMoney;

    private static final long serialVersionUID = 1L;
}