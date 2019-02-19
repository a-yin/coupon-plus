package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.ResultVO;

/**
 * 获取优惠券库存 Service接口，即只返回用户没有领取的
 *
 * @author lingb
 * @date 2018.11.21 21:55
 */
public interface IInventoryService {

    /**
     * 获取库存信息
     *
     * @param userId  用户 id
     * @return {@link ResultVO}
     * @throws Exception
     */
    ResultVO listInventory(Long userId) throws Exception;
}
