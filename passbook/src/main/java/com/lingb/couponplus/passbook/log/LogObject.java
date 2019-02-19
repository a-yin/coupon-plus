package com.lingb.couponplus.passbook.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志对象
 *
 * @author lingb
 * @date 2018.11.18 18:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogObject {

    /**
     * 日志动作类型
     */
    private String action;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 当前时间戳
     */
    private Long timeStamp;

    /**
     * 客户端ip 地址
     */
    private String remoteIp;

    /**
     * 日志信息
     */
    private Object info = null;

}
