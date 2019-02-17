package com.lingb.couponplus.merchants.service;

import com.lingb.couponplus.merchants.vo.MerchantsVO;
import com.lingb.couponplus.merchants.vo.PassTemplateVO;
import com.lingb.couponplus.merchants.vo.ResultVO;

/**
 * Merchants 业务接口
 *
 * @author lingb
 * @date 2011.11.16 17:38
 */
public interface IMerchantsService {

    /**
     * 创建商户
     *
     * @param merchantsVO {@link MerchantsVO} 商户VO
     * @return {@link ResultVO}
     */
    ResultVO createMerchants(MerchantsVO merchantsVO);

    /**
     * 根据商户 id 获取商户
     *
     * @param id 商户 id
     * @return {@link ResultVO}
     */
    ResultVO getMerchantsById(Integer id);

    /**
     * 投放优惠券
     *
     * @param passTemplateVO {@link PassTemplateVO} 优惠券VO
     * @return {@link ResultVO}
     */
    ResultVO dropPassTemplate(PassTemplateVO passTemplateVO);
}
