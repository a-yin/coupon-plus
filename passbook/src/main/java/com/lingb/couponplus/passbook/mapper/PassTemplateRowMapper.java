package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 PassTemplateVO 映射 PassTemplateVO
 *
 * @author lingb
 * @date 2018.11.19 16:15
 */
public class PassTemplateRowMapper implements RowMapper<PassTemplateVO> {

    @Override
    public PassTemplateVO mapRow(Result result, int rowNum) throws Exception {

        PassTemplateVO passTemplateVO = new PassTemplateVO();

        // PassTemplateVO 中 BaseInfo列族
        passTemplateVO.setId(Bytes.toInt(result.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.ID_BYTE)));
        passTemplateVO.setTitle(Bytes.toString(result.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.TITLE_BYTE)));
        passTemplateVO.setSummary(Bytes.toString(result.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.SUMMARY_BYTE)));
        passTemplateVO.setDesc(Bytes.toString(result.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.DESC_BYTE)));
        passTemplateVO.setHasToken(Bytes.toBoolean(result.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.HAS_TOKEN_BYTE)));
        passTemplateVO.setBackground(Bytes.toInt(result.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.BACKGROUND_BYTE)));

        String[] patterns = new String[] {"yyyy-MM-dd"};

        // PassTemplateVO 中 OtherInfo列族
        passTemplateVO.setLimit(Bytes.toLong(result.getValue(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.LIMIT_BYTE)));
        passTemplateVO.setStart(DateUtils.parseDate(Bytes.toString(result.getValue(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.START_BYTE)), patterns));
        passTemplateVO.setEnd(DateUtils.parseDate(Bytes.toString(result.getValue(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.END_BYTE)), patterns));

        return passTemplateVO;
    }
}
