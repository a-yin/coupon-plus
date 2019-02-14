package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constants.Constants;
import com.lingb.couponplus.passbook.vo.Pass;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 Pass 映射 PassVO
 *
 * @author lingb
 * @date 2019.02.14 16:06
 */
public class PassRowMapper implements RowMapper<Pass> {

    /**
     * Pass 列族
     */
    private static byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();
    private static byte[] USER_ID = Constants.PassTable.USER_ID.getBytes();
    private static byte[] TEMPLATE_ID = Constants.PassTable.TEMPLATE_ID.getBytes();
    private static byte[] TOKEN = Constants.PassTable.TOKEN.getBytes();
    private static byte[] ASSIGNED_DATE = Constants.PassTable.ASSIGNED_DATE.getBytes();
    private static byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();

    @Override
    public Pass mapRow(Result result, int rowNum) throws Exception {
        Pass pass = new Pass();
        pass.setRowKey(Bytes.toString(result.getRow()));
        pass.setUserId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)));
        pass.setTemplateId(Bytes.toString(result.getValue(FAMILY_I, TEMPLATE_ID)));
        pass.setToken(Bytes.toString(result.getValue(FAMILY_I, TOKEN)));

        String[] patterns = new String[]{"yyyy-mm-dd"};
        pass.setAssignedDate(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_I, ASSIGNED_DATE)), patterns));

        String conDate = Bytes.toString(result.getValue(FAMILY_I, CON_DATE));
        if (conDate.equals(-1)) {
            pass.setConDate(null);

        } else {
            pass.setConDate(DateUtils.parseDate(conDate, patterns));
        }

        return pass;
    }
}
