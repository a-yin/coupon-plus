package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.vo.FeedbackVO;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 FeedbackVO 映射 FeedbackVO
 *
 * @author lingb
 * @date 2018.11.14 16:15
 */
public class FeedbackRowMapper implements RowMapper<FeedbackVO> {

    @Override
    public FeedbackVO mapRow(Result result, int rowNum) throws Exception {

        FeedbackVO feedbackVO = new FeedbackVO();
        feedbackVO.setUserId(Bytes.toLong(result.getValue(HBaseTable.Feedback.FAMILY_I_BYTE, HBaseTable.Feedback.USER_ID_BYTE)));
        feedbackVO.setType(Bytes.toString(result.getValue(HBaseTable.Feedback.FAMILY_I_BYTE, HBaseTable.Feedback.TYPE_BYTE)));
        feedbackVO.setTemplateId(Bytes.toString(result.getValue(HBaseTable.Feedback.FAMILY_I_BYTE, HBaseTable.Feedback.TEMPLATE_ID_BYTE)));
        feedbackVO.setComment(Bytes.toString(result.getValue(HBaseTable.Feedback.FAMILY_I_BYTE, HBaseTable.Feedback.COMMENT_BYTE)));

        return feedbackVO;
    }
}
