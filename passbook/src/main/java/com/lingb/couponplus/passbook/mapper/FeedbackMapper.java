package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.Commons;
import com.lingb.couponplus.passbook.vo.Feedback;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 Feedback 映射 FeedbackVO
 *
 * @author lingb
 * @date 2018.11.14 16:15
 */
public class FeedbackMapper implements RowMapper<Feedback> {

    /**
     * Feedback 列族
     */
    private static byte[] FAMILY_I = Commons.Feedback.FAMILY_I.getBytes();
    private static byte[] USER_ID = Commons.Feedback.USER_ID.getBytes();
    private static byte[] TYPE = Commons.Feedback.TYPE.getBytes();
    private static byte[] TEMPLATE_ID = Commons.Feedback.TEMPLATE_ID.getBytes();
    private static byte[] COMMENT = Commons.Feedback.COMMENT.getBytes();

    @Override
    public Feedback mapRow(Result result, int rowNum) throws Exception {

        Feedback feedback = new Feedback();
        feedback.setUserId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)));
        feedback.setType(Bytes.toString(result.getValue(FAMILY_I, TYPE)));
        feedback.setTemplateId(Bytes.toString(result.getValue(FAMILY_I, TEMPLATE_ID)));
        feedback.setComment(Bytes.toString(result.getValue(FAMILY_I, COMMENT)));

        return feedback;
    }
}
