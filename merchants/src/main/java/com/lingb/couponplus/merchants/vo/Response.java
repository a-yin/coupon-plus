package com.lingb.couponplus.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应对象
 *
 * @author lingb
 * @date 2019.02.15 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private Integer code;

    private String msg;

    private Object data;

    public Response(Object data) {
        this.data = data;
    }
}
