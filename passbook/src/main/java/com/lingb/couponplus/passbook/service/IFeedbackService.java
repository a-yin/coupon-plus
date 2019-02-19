package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.FeedbackVO;
import com.lingb.couponplus.passbook.vo.ResultVO;

/**
 * 用户反馈 Service 接口
 *
 * @author lingb
 * @date 2018.11.21 23:30
 */
public interface IFeedbackService {

    /**
     * 创建用户反馈
     *
     * @param feedbackVO {@link FeedbackVO}
     * @return {@link ResultVO}
     */
    ResultVO createFeedback(FeedbackVO feedbackVO);

    /**
     * 获取用户反馈
     *
     * @param userId 用户 id
     * @return {@link ResultVO}
     */
    ResultVO listFeedback(Long userId);
}
