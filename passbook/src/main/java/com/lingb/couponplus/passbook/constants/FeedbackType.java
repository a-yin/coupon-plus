package com.lingb.couponplus.passbook.constants;

/**
 * 反馈类型
 *
 * @author lingb
 * @date 2019.02.13 15:24
 */
public enum FeedbackType {

    PASS(1, "针对优惠券的反馈"),
    APP(2, "针对卡包 APP 的反馈");

    /**
     * 反馈类型编码
     */
    private Integer code;

    /**
     * 反馈类型描述
     */
    private String desc;

    FeedbackType(Integer code, String desc) {
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
