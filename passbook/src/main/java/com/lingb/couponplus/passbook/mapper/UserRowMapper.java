package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.vo.UserVO;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 UserVO 映射 UserVO
 *
 * @author lingb
 * @date 2018.11.19 14:02
 */
public class UserRowMapper implements RowMapper<UserVO> {

    @Override
    public UserVO mapRow(Result result, int rowNum) throws Exception {
        // UserVO 中 BaseInfo列族
        UserVO.BaseInfo baseInfo = new UserVO.BaseInfo(
                Bytes.toString(result.getValue(HBaseTable.UserTable.FAMILY_B_BYTE, HBaseTable.UserTable.NAME_BYTE)),
                Bytes.toInt(result.getValue(HBaseTable.UserTable.FAMILY_B_BYTE, HBaseTable.UserTable.AGE_BYTE)),
                Bytes.toString(result.getValue(HBaseTable.UserTable.FAMILY_B_BYTE, HBaseTable.UserTable.SEX_BYTE))
        );

        // UserVO 中 OtherInfo列族
        UserVO.OtherInfo otherInfo = new UserVO.OtherInfo(
                Bytes.toString(result.getValue(HBaseTable.UserTable.FAMILY_O_BYTE, HBaseTable.UserTable.PHONE_BYTE)),
                Bytes.toString(result.getValue(HBaseTable.UserTable.FAMILY_O_BYTE, HBaseTable.UserTable.ADDRESS_BYTE))
        );

        return new UserVO(
                Bytes.toLong(result.getRow()), baseInfo, otherInfo
        );
    }
}
