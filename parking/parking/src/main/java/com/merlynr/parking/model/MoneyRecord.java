package com.merlynr.parking.model;

import java.io.Serializable;
import lombok.Data;

/**
 * money_record
 * @author 
 */
@Data
public class MoneyRecord implements Serializable {
    private Integer id;

    private String time;

    private String money;

    /**
     * 存在为月租
     */
    private Integer user;

    /**
     * 存在为临时
     */
    private Integer record;

    private static final long serialVersionUID = 1L;
}