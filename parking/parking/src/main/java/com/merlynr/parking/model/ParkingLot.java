package com.merlynr.parking.model;

import java.io.Serializable;
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

    private String name;

    private String type;

    /**
     * 车位创建日期
     */
    private String time;

    private String used;

    private static final long serialVersionUID = 1L;
}