package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.PassVO;
import com.lingb.couponplus.passbook.vo.ResultVO;

/**
 * 用户优惠券 Service 接口
 *
 * @author lingb
 * @date 2018.11.19 20:10
 */
public interface IUserPassService {

    /**
     * 获取用户未被使用的优惠券, 即我的优惠券
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     * @throws Exception
     */
    ResultVO listUserPass(Long userId) throws Exception;

    /**
     * 获取用户已经使用的优惠券
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     * @throws Exception
     */
    ResultVO listUserUsedPass(Long userId) throws Exception;

    /**
     * 获取用户所有优惠券（未被使用 + 已经使用）
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     * @throws Exception
     */
    ResultVO listUserAllPass(Long userId) throws Exception;

    /**
     * 用户使用优惠券
     *
     * @param passVO {@link PassVO}
     * @return {@link ResultVO}
     */
    ResultVO userUsePass(PassVO passVO);
}
