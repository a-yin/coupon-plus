package com.lingb.couponplus.merchants.enum;

/**
 * 错误码枚举
 *
 * @author lingb
 * @date 2019.11.15 11:31
 */
public enum ErrorCodeEnum {

    SUCCESS(0, ""),
    DUPLICATE_NAME(1, "商户名字重复"),
    EMPTY_LOGO(2, "商户 logo 为空"),
    EMPTY_BUSINESS_LICENSE(3, "商户营业执照为空"),
    ERROR_PHONE(4, "商户联系电话错误"),
    EMPTY_ADDRESS(5, "商户地址为空"),
    MERCHANTS_NOT_EXIST(6, "商户不存在");

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码描述
     */
    private String desc;

    ErrorCodeEnum(Integer code, String dsc) {
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

