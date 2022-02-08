package com.merlynr.parking.model;

import java.io.Serializable;
import lombok.Data;

/**
 * money
 * @author 
 */
@Data
public class Money implements Serializable {
    private Integer id;

    /**
     * 1月租2临时起步价3临时每小时多少钱4临时全天
     */
    private String type;

    /**
     * 钱数
     */
    private String money;

    private static final long serialVersionUID = 1L;
}