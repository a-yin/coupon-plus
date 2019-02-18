package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.GainPassTemplateReqVO;
import com.lingb.couponplus.passbook.vo.ResultVO;

/**
 * 用户领取优惠券请求 Service 接口
 *
 * @author lingb
 * @date 2018.11.18 23:40
 */
public interface IGainPassTemplateService {

    /**
     * <h2>用户领取优惠券请求</h2>
     * @param request {@link GainPassTemplateReqVO}
     * @return {@link Response}
     * @throws Exception
     */
    ResultVO gainPassTemplate(GainPassTemplateReqVO request) throws Exception;
}
