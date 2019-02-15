package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.Commons;
import com.lingb.couponplus.passbook.vo.PassTemplate;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;


/**
 * HBase数据库 PassTemplate 映射 PassTemplateVO
 *
 * @author lingb
 * @date 2018.11.14 16:15
 */
public class PassTemplateMapper implements RowMapper<PassTemplate> {

    /**
     * PassTemplate 中 BaseInfo列族
     */
    private static byte[] FAMILY_B = Commons.PassTemplateTable.FAMILY_B.getBytes();
    private static byte[] ID = Commons.PassTemplateTable.ID.getBytes();
    private static byte[] TITLE = Commons.PassTemplateTable.TITLE.getBytes();
    private static byte[] SUMMARY = Commons.PassTemplateTable.SUMMARY.getBytes();
    private static byte[] DESC = Commons.PassTemplateTable.DESC.getBytes();
    private static byte[] HAS_TOKEN = Commons.PassTemplateTable.HAS_TOKEN.getBytes();
    private static byte[] BACKGROUND = Commons.PassTemplateTable.BACKGROUND.getBytes();

    /**
     * PassTemplate 中 OtherInfo列族
     */
    private static byte[] FAMILY_C = Commons.PassTemplateTable.FAMILY_C.getBytes();
    private static byte[] LIMIT = Commons.PassTemplateTable.LIMIT.getBytes();
    private static byte[] START = Commons.PassTemplateTable.START.getBytes();
    private static byte[] END = Commons.PassTemplateTable.END.getBytes();

    @Override
    public PassTemplate mapRow(Result result, int rowNum) throws Exception {

        PassTemplate passTemplate = new PassTemplate();

        passTemplate.setId(Bytes.toInt(result.getValue(FAMILY_B, ID)));
        passTemplate.setTitle(Bytes.toString(result.getValue(FAMILY_B, TITLE)));
        passTemplate.setSummary(Bytes.toString(result.getValue(FAMILY_B, SUMMARY)));
        passTemplate.setDesc(Bytes.toString(result.getValue(FAMILY_B, DESC)));
        passTemplate.setHasToken(Bytes.toBoolean(result.getValue(FAMILY_B, HAS_TOKEN)));
        passTemplate.setBackground(Bytes.toInt(result.getValue(FAMILY_B, BACKGROUND)));

        String[] patterns = new String[] {"yyyy-MM-dd"};

        passTemplate.setLimit(Bytes.toLong(result.getValue(FAMILY_C, LIMIT)));
        passTemplate.setStart(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, START)), patterns));
        passTemplate.setEnd(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, END)), patterns));

        return passTemplate;
    }
}
