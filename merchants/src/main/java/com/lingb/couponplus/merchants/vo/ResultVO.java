package com.lingb.couponplus.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果对象VO
 *
 * @author lingb
 * @date 2018.11.15 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    private Integer code;

    private String msg;

    private Object data;

    public ResultVO(Object data) {
        this.data = data;
    }
}
