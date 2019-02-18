package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果 VO
 *
 * @author lingb
 * @date 2019.02.18 13:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    /**
     * 错误码: 正确返回 0
     */
    private Integer errorCode = 0;

    /**
     * 错误信息，正确返回空字符串
     */
    private String errorMsg = "";

    /** 返回值对象 */
    private Object data;

    /**
     * <h2>正确的响应构造函数</h2>
     * */
    public ResultVO(Object data) {
        this.data = data;
    }

    /**
     * <h2>空响应</h2>
     * */
    public static ResultVO success() {
        return new ResultVO();
    }

    /**
     * <h2>错误响应</h2>
     * */
    public static ResultVO failure(String errorMsg) {
        return new ResultVO(-1, errorMsg, null);
    }
}
