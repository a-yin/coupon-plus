package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.GainPassTemplateReqVO;
import com.lingb.couponplus.passbook.vo.ResultVO;

/**
 * 用户领取优惠券请求 Service 接口
 *
 * @author lingb
 * @date 2018.11.20 23:40
 */
public interface IGainPassTemplateService {

    /**
     * 用户领取优惠券
     *
     * @param request {@link GainPassTemplateReqVO}
     * @return {@link ResultVO}
     * @throws Exception
     */
    ResultVO gainPassTemplate(GainPassTemplateReqVO request) throws Exception;
}
