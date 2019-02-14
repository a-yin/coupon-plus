package com.lingb.couponplus.passbook.vo;

import com.google.common.base.Enums;
import com.lingb.couponplus.passbook.constants.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户反馈
 *
 * @author lingb
 * @date 2019.02.14 13:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 反馈类型
     */
    private String type;

    /**
     * PassTemplate RowKey, 若是 app 反馈，则为 null
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
        FeedbackType feedbackType = Enums.getIfPresent(
                FeedbackType.class, this.type.toUpperCase()
        ).orNull();
//        return !(null == feedbackType || null == comment);
      return (null != feedbackType && null != comment);
    }
}
