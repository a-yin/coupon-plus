package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常信息 VO
 *
 * @author lingb
 * @date 2018.11.18 10:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVO {

    /**
     * 错误码 -1
     */
    public static final Integer ERROR = -1;

    /**
     * 特定错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 请求的 url
     */
    private String url;

    /**
     * 响应（返回）的数据
     */
    private Object data;

}
