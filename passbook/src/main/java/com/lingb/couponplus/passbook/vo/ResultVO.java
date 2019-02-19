package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果 VO
 *
 * @author lingb
 * @date 2018.11.18 13:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /** 返回值对象 */
    private Object data;

    /**
     * 正确响应构造函数，
     * 返回码正确返回 0
     * 返回信息正确返回 ""
     */
    public ResultVO(Object data) {
        this.code = 0;
        this.msg = "";
        this.data = data;
    }

    /**
     * 空响应
     */
    public static ResultVO success() {
        return new ResultVO();
    }

    /**
     * 错误响应
     */
    public static ResultVO failure(String errorMsg) {
        return new ResultVO(-1, errorMsg, null);
    }
}
