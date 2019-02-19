package com.lingb.couponplus.passbook.vo;

import com.google.common.base.Enums;
import com.lingb.couponplus.passbook.constant.FeedbackTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户反馈信息 VO
 *
 * @author lingb
 * @date 2018.11.18 13:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackVO {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 反馈类型
     */
    private String type;

    /**
     * PassTemplateVO RowKey, 若是 app 反馈，则为 null
     */
    private String templateId;

    /**
     * 反馈内容
     */
    private String comment;

    /**
     * 验证用户反馈类型
     * @return
     */
    public boolean validate() {
        FeedbackTypeEnum feedbackTypeEnum = Enums.getIfPresent(
                FeedbackTypeEnum.class, this.type.toUpperCase()
        ).orNull();
//        return !(null == feedbackTypeEnum || null == comment);
      return (null != feedbackTypeEnum && null != comment);
    }
}
