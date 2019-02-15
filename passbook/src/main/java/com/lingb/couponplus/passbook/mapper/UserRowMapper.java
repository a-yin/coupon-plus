package com.lingb.couponplus.passbook.mapper;

import com.lingb.couponplus.passbook.constant.Commons;
import com.lingb.couponplus.passbook.vo.User;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase数据库 User 映射 UserVO
 *
 * @author lingb
 * @date 2018.11.14 14:02
 */
public class UserRowMapper implements RowMapper<User> {

    // 字符流 --> 字节流
    /**
     * User 中 BaseInfo 列族
     */
    private static byte[] FAMILY_B = Commons.UserTable.FAMILY_B.getBytes();
    private static byte[] NAME = Commons.UserTable.NAME.getBytes();
    private static byte[] AGE = Commons.UserTable.AGE.getBytes();
    private static byte[] SEX = Commons.UserTable.SEX.getBytes();

    /**
     * User 中 OtherInfo 列族
     */
    private static byte[] FAMILY_O = Commons.UserTable.FAMILY_O.getBytes();
    private static byte[] PHONE = Commons.UserTable.PHONE.getBytes();
    private static byte[] ADDRESS = Commons.UserTable.ADDRESS.getBytes();

    @Override
    public User mapRow(Result result, int rowNum) throws Exception {
        User.BaseInfo baseInfo = new User.BaseInfo(
                Bytes.toString(result.getValue(FAMILY_B, NAME)),
                Bytes.toInt(result.getValue(FAMILY_B, AGE)),
                Bytes.toString(result.getValue(FAMILY_B, SEX))
        );
        User.OtherInfo otherInfo = new User.OtherInfo(
                Bytes.toString(result.getValue(FAMILY_O, PHONE)),
                Bytes.toString(result.getValue(FAMILY_O, ADDRESS))
        );

        return new User(
                Bytes.toLong(result.getRow()), baseInfo, otherInfo
        );
    }
}
