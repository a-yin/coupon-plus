package com.lingb.couponplus.passbook.util;

import com.lingb.couponplus.passbook.vo.FeedbackVO;
import com.lingb.couponplus.passbook.vo.GainPassTemplateReqVO;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * RowKey 生成器工具类
 *
 * @author lingb
 * @date 2018.11.18 11:21
 */
@Slf4j
public class RowKeyGenUtil {

    /**
     * 根据提供的 PassTemplate 对象生成 RowKey
     * PassTemplate 的 RowKey = 商户 id + "_" + 优惠券标题
     *
     * @param passTemplate {@link PassTemplate}
     * @return String RowKey
     */
    public static String genPassTemplateRowKey(PassTemplateVO passTemplateVO) {

        String passInfo = String.valueOf(passTemplateVO.getId()) + "_" + passTemplateVO.getTitle();
        String rowKey = DigestUtils.md5Hex(passInfo);
        log.info("GenPassTemplateRowKey: {}, {}", passInfo, rowKey);

        return rowKey;
    }

    /**
     * 根据提供的领取优惠券请求生成 RowKey, 只可以在领取优惠券的时候使用
     * Pass的 RowKey = reversed(userId) + inverse(timestamp) + PassTemplate RowKey
     *
     * @param request {@link GainPassTemplateRequest}
     * @return String RowKey
     */
    public static String genPassRowKey(GainPassTemplateReqVO request) {

        return new StringBuilder(String.valueOf(request.getUserId())).reverse().toString()
                + (Long.MAX_VALUE - System.currentTimeMillis())
                + genPassTemplateRowKey(request.getPassTemplateVO());
    }

    /**
     * 根据 Feedback 构造 RowKey
     *
     * @param feedback {@link FeedbackVO}
     * @return String RowKey
     */
    public static String genFeedbackRowKey(FeedbackVO feedbackVO) {

        return new StringBuilder(String.valueOf(feedbackVO.getUserId())).reverse().toString() +
                (Long.MAX_VALUE - System.currentTimeMillis());
    }
}
