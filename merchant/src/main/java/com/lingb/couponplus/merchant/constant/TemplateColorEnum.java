package com.lingb.couponplus.merchant.constant;

/**
 * 优惠券背景色枚举
 *
 * @author lingb
 * @date 2018.11.15 11:25
 */
public enum TemplateColorEnum {

    RED(1, "红色"),
    GREEN(2, "绿色"),
    BLUE(3, "蓝色");

    /**
     * 颜色代码
     */
    private Integer code;

    /**
     * 颜色描述
     */
    private String color;

    TemplateColorEnum(Integer code, String color) {
        this.code = code;
        this.color = color;
    }

    public Integer getCode() {
        return code;
    }

    public String getColor() {
        return color;
    }
}
