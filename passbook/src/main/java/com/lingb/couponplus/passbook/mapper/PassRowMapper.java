package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.vo.PassVO;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 PassVO 映射 PassVO
 *
 * @author lingb
 * @date 2018.11.14 16:06
 */
public class PassRowMapper implements RowMapper<PassVO> {

    @Override
    public PassVO mapRow(Result result, int rowNum) throws Exception {
        PassVO passVO = new PassVO();
        passVO.setRowKey(Bytes.toString(result.getRow()));
        passVO.setUserId(Bytes.toLong(result.getValue(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.USER_ID_BYTE)));
        passVO.setTemplateId(Bytes.toString(result.getValue(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.TEMPLATE_ID_BYTE)));
        passVO.setToken(Bytes.toString(result.getValue(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.TOKEN_BYTE)));

        String[] patterns = new String[]{"yyyy-mm-dd"};
        passVO.setAssignedDate(DateUtils.parseDate(
                Bytes.toString(result.getValue(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.ASSIGNED_DATE_BYTE)), patterns));

        String conDate = Bytes.toString(result.getValue(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.CON_DATE_BYTE));
        if (conDate.equals(-1)) {
            passVO.setConDate(null);

        } else {
            passVO.setConDate(DateUtils.parseDate(conDate, patterns));
        }

        return passVO;
    }
}
