package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.ResultVO;
import com.lingb.couponplus.passbook.vo.UserVO;

/**
 * 用户 Service 接口
 *
 * @author lingb
 * @date 2018.11.20 22:49
 */
public interface IUserService {

    /**
     * 创建用户
     *
     * @param userVO {@link UserVO}
     * @return {@link ResultVO}
     * @throws Exception
     */
    ResultVO createUser(UserVO userVO) throws Exception;
}
