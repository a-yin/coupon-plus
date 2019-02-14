package com.lingb.couponplus.passbook.constants;

/**
 * 优惠券状态
 *
 * @author lingb
 * @date 2019.02.13 15:21
 */
public enum PassStatus {
    UNUSED(1, "未被使用"),
    USED(2, "已经使用的"),
    ALL(3, "全部领取的");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String desc;

    PassStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
