package com.merlynr.parking.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * users
 * @author 
 */
@Data
public class Users implements Serializable {
    private Integer id;

    private String userName;

    private String pwd;

    /**
     * 0管理者1月租用户
     */
    private String role;

    /**
     * 车牌
     */
    private String licensePlates;

    private String name;

    private String tel;

    private Date startTime;

    private Date endTime;

    /**
     * 车位编码
     */
    private String parkingLotId;

    /**
     * 行驶证，base64
     */
    private String license;

    private static final long serialVersionUID = 1L;

}