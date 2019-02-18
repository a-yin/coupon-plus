package com.lingb.couponplus.passbook.service.impl;

import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.service.IHBasePassService;
import com.lingb.couponplus.passbook.util.RowKeyGenUtil;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * HBasePass Service接口的实现
 *
 * @author lingb
 * @date 2019.02.18 10:46
 */
@Slf4j
@Service
public class HBasePassServiceImpl implements IHBasePassService {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Override
    public boolean dropPassTemplateToHBase(PassTemplateVO passTemplateVO) {

        if (null == passTemplateVO) {
            // 可定义Error 返回
            return false;
        }

        String rowKey = RowKeyGenUtil.genPassTemplateRowKey(passTemplateVO);

        try {
            // 判断 HBase 是否已存在该 rowkey
            boolean isExisted = hbaseTemplate.getConnection().getTable(
                    TableName.valueOf(HBaseTable.PassTemplateTable.TABLE_NAME))
                    .exists(new Get(Bytes.toBytes(rowKey)));
            if (isExisted) {
                log.warn("RowKey {} is already exist!", rowKey);
                return false;
            }
        } catch (IOException e) {
            log.error("DropPassTemplateToHBase Error: {}", e.getMessage());
            return false;
        }

        // 创建 Put对象
        Put put = new Put(Bytes.toBytes(rowKey));

        // 基本信息列族
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_B_BYTE,
                HBaseTable.PassTemplateTable.ID_BYTE,
                Bytes.toBytes(passTemplateVO.getId())
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_B_BYTE,
                HBaseTable.PassTemplateTable.TITLE_BYTE,
                Bytes.toBytes(passTemplateVO.getTitle())
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_B_BYTE,
                HBaseTable.PassTemplateTable.SUMMARY_BYTE,
                Bytes.toBytes(passTemplateVO.getSummary())
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_B_BYTE,
                HBaseTable.PassTemplateTable.DESC_BYTE,
                Bytes.toBytes(passTemplateVO.getDesc())
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_B_BYTE,
                HBaseTable.PassTemplateTable.HAS_TOKEN_BYTE,
                Bytes.toBytes(passTemplateVO.getHasToken())
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_B_BYTE,
                HBaseTable.PassTemplateTable.BACKGROUND_BYTE,
                Bytes.toBytes(passTemplateVO.getBackground())
        );

        // 约束信息列族
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_C_BYTE,
                HBaseTable.PassTemplateTable.LIMIT_BYTE,
                Bytes.toBytes(passTemplateVO.getLimit())
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_C_BYTE,
                HBaseTable.PassTemplateTable.START_BYTE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(passTemplateVO.getStart()))
        );
        put.addColumn(
                HBaseTable.PassTemplateTable.FAMILY_C_BYTE,
                HBaseTable.PassTemplateTable.END_BYTE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(passTemplateVO.getEnd()))
        );

        // 将put 对象写入 HBase
        hbaseTemplate.saveOrUpdate(HBaseTable.PassTemplateTable.TABLE_NAME, put);

        return true;
    }
}
