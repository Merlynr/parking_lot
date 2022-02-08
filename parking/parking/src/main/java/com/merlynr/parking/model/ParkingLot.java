package com.merlynr.parking.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * parking_lot
 * @author 
 */
@Data
public class ParkingLot implements Serializable {
    /**
     * 车位表
     */
    private Integer id;

    private String type;

    /**
     * 车位创建日期
     */
    private Date time;

    private static final long serialVersionUID = 1L;
}