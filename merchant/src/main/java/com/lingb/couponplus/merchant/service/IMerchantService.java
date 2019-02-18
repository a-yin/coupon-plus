package com.lingb.couponplus.merchant.service;

import com.lingb.couponplus.merchant.vo.MerchantVO;
import com.lingb.couponplus.merchant.vo.PassTemplateVO;
import com.lingb.couponplus.merchant.vo.ResultVO;

/**
 * Merchant 业务接口
 *
 * @author lingb
 * @date 2011.11.16 17:38
 */
public interface IMerchantService {

    /**
     * 创建商户
     *
     * @param merchantVO {@link MerchantVO} 商户VO
     * @return {@link ResultVO}
     */
    ResultVO createMerchants(MerchantVO merchantVO);

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
