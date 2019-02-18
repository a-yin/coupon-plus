package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户领取优惠券请求 VO
 *
 * @author lingb
 * @date 2018.11.18 11:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainPassTemplateReqVO {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * PassTemplate 对象
     */
    private PassTemplateVO passTemplateVO;
}
