package com.lingb.couponplus.passbook.service.impl;

import com.lingb.couponplus.passbook.constant.Commons;
import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.service.IUserService;
import com.lingb.couponplus.passbook.vo.ResultVO;
import com.lingb.couponplus.passbook.vo.UserVO;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 Service 接口的实现
 *
 * @author lingb
 * @date 2019.02.18 22:51
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    /**
     * HBase 客户端
     */
    private final HbaseTemplate hbaseTemplate;

    /**
     * redis 客户端
     */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public UserServiceImpl(HbaseTemplate hbaseTemplate, StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ResultVO createUser(UserVO userVO) throws Exception {

        Long curCount = redisTemplate.opsForValue().increment(Commons.USE_COUNT_REDIS_KEY, 1);
        Long userId = genUserId(curCount);

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(Bytes.toBytes(userId));

        // 基本信息列族
        put.addColumn(HBaseTable.UserTable.FAMILY_B_BYTE, HBaseTable.UserTable.NAME_BYTE,
                Bytes.toBytes(userVO.getBaseInfo().getName()));
        put.addColumn(HBaseTable.UserTable.FAMILY_B_BYTE, HBaseTable.UserTable.AGE_BYTE,
                Bytes.toBytes(userVO.getBaseInfo().getAge()));
        put.addColumn(HBaseTable.UserTable.FAMILY_B_BYTE, HBaseTable.UserTable.SEX_BYTE,
                Bytes.toBytes(userVO.getBaseInfo().getSex()));

        // 额外信息列族
        put.addColumn(HBaseTable.UserTable.FAMILY_O_BYTE, HBaseTable.UserTable.PHONE_BYTE,
                Bytes.toBytes(userVO.getOtherInfo().getPhone()));
        put.addColumn(HBaseTable.UserTable.FAMILY_O_BYTE, HBaseTable.UserTable.ADDRESS_BYTE,
                Bytes.toBytes(userVO.getOtherInfo().getAddress()));

        datas.add(put);

        // 将Put 写入 HBase
        hbaseTemplate.saveOrUpdates(HBaseTable.UserTable.TABLE_NAME, datas);

        userVO.setId(userId);

        return new ResultVO(userVO);
    }

    /**
     * 生成 userId
     *
     * @param prefix 当前用户数
     * @return 用户 id
     * */
    private Long genUserId(Long prefix) {

        String suffix = RandomStringUtils.randomNumeric(5);
        return Long.valueOf(prefix + suffix);
    }
}
