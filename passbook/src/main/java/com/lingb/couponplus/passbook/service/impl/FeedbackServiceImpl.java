package com.lingb.couponplus.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.mapper.FeedbackRowMapper;
import com.lingb.couponplus.passbook.service.IFeedbackService;
import com.lingb.couponplus.passbook.util.RowKeyGenUtil;
import com.lingb.couponplus.passbook.vo.FeedbackVO;
import com.lingb.couponplus.passbook.vo.ResultVO;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户反馈 Service 接口的实现
 *
 * @author lingb
 * @date 2018.11.21 23:33
 */
@Slf4j
@Service
public class FeedbackServiceImpl implements IFeedbackService {

    /**
     * HBase 客户端
     */
    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public FeedbackServiceImpl(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public ResultVO createFeedback(FeedbackVO feedbackVO) {

        if (!feedbackVO.validate()) {
            log.error("Feedback Error: {}", JSON.toJSONString(feedbackVO));
            return ResultVO.failure("Feedback Error");
        }

        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genFeedbackRowKey(feedbackVO)));

        put.addColumn(
                Bytes.toBytes(HBaseTable.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTable.Feedback.USER_ID),
                Bytes.toBytes(feedbackVO.getUserId())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTable.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTable.Feedback.TYPE),
                Bytes.toBytes(feedbackVO.getType())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTable.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTable.Feedback.TEMPLATE_ID),
                Bytes.toBytes(feedbackVO.getTemplateId())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTable.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTable.Feedback.COMMENT),
                Bytes.toBytes(feedbackVO.getComment())
        );

        hbaseTemplate.saveOrUpdate(HBaseTable.Feedback.TABLE_NAME, put);

        return ResultVO.success();
    }

    @Override
    public ResultVO listFeedback(Long userId) {

        byte[] reverseUserId = new StringBuilder(String.valueOf(userId)).reverse().toString().getBytes();
        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(reverseUserId));

        List<FeedbackVO> feedbackVOs = hbaseTemplate.find(HBaseTable.Feedback.TABLE_NAME, scan, new FeedbackRowMapper());

        return new ResultVO(feedbackVOs);
    }
}
