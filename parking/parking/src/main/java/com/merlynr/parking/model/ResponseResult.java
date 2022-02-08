package com.merlynr.parking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * respnse统一结构体
 *
 * @author merlynr
 * @date 2021/03/15
 */
@Data
@AllArgsConstructor
public class ResponseResult implements Serializable {

    private Integer code;
    private String msg;
    private Object data;
}
