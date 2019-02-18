package com.lingb.couponplus.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.passbook.constant.Commons;
import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.mapper.PassTemplateRowMapper;
import com.lingb.couponplus.passbook.service.IGainPassTemplateService;
import com.lingb.couponplus.passbook.util.RowKeyGenUtil;
import com.lingb.couponplus.passbook.vo.GainPassTemplateReqVO;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import com.lingb.couponplus.passbook.vo.ResultVO;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户领取优惠券请求 Service 接口的实现
 *
 * @author lingb
 * @date 2018.11.18 23:43
 */
@Slf4j
@Service
public class GainPassTemplateServiceImpl implements IGainPassTemplateService {

    /**
     * HBase 客户端
     */
    private final HbaseTemplate hbaseTemplate;

    /**
     * Redis 客户端
     */
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public GainPassTemplateServiceImpl(HbaseTemplate hbaseTemplate,
                                       StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ResultVO gainPassTemplate(GainPassTemplateReqVO request) throws Exception {

        PassTemplateVO passTemplate = null;
        String passTemplateId = RowKeyGenUtil.genPassTemplateRowKey(
                request.getPassTemplateVO());

        try {
            passTemplate = hbaseTemplate.get(
                    HBaseTable.PassTemplateTable.TABLE_NAME,
                    passTemplateId,
                    new PassTemplateRowMapper()
            );
        } catch (Exception ex) {
            log.error("Gain PassTemplate Error: {}",
                    JSON.toJSONString(request.getPassTemplateVO()));
            return ResultVO.failure("Gain PassTemplate Error!");
        }

        if (passTemplate.getLimit() <= 1 && passTemplate.getLimit() != -1) {
            log.error("PassTemplate Limit Max: {}",
                    JSON.toJSONString(request.getPassTemplateVO()));
            return ResultVO.failure("PassTemplate Limit Max!");
        }

        Date cur = new Date();
        if (!(cur.getTime() >= passTemplate.getStart().getTime()
                && cur.getTime() < passTemplate.getEnd().getTime())) {
            log.error("PassTemplate ValidTime Error: {}",
                    JSON.toJSONString(request.getPassTemplateVO()));
            return ResultVO.failure("PassTemplate ValidTime Error!");
        }

        // 减去优惠券的 limit
        if (passTemplate.getLimit() != -1) {
            List<Mutation> datas = new ArrayList<>();

            Put put = new Put(Bytes.toBytes(passTemplateId));
            put.addColumn(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.LIMIT_BYTE,
                    Bytes.toBytes(passTemplate.getLimit() - 1));
            datas.add(put);

            hbaseTemplate.saveOrUpdates(HBaseTable.PassTemplateTable.TABLE_NAME,
                    datas);
        }

        // 将优惠券保存到用户优惠券表
        if (!addPassForUser(request, passTemplate.getId(), passTemplateId)) {
            return ResultVO.failure("GainPassTemplate Failure!");
        }

        return ResultVO.success();
    }

    /**
     * <h2>给用户添加优惠券</h2>
     *
     * @param request        {@link GainPassTemplateReqVO}
     * @param merchantsId    商户 id
     * @param passTemplateId 优惠券 id
     * @return true/false
     */
    private boolean addPassForUser(GainPassTemplateReqVO request,
                                   Integer merchantsId, String passTemplateId) throws Exception {

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genPassRowKey(request)));
        put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.USER_ID_BYTE,
                Bytes.toBytes(request.getUserId()));
        put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.TEMPLATE_ID_BYTE,
                Bytes.toBytes(passTemplateId));

        if (request.getPassTemplateVO().getHasToken()) {
            String token = redisTemplate.opsForSet().pop(passTemplateId);
            if (null == token) {
                log.error("Token not exist: {}", passTemplateId);
                return false;
            }
            recordTokenToFile(merchantsId, passTemplateId, token);
            put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.TOKEN_BYTE,
                    Bytes.toBytes(token));
        } else {
            put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.TOKEN_BYTE,
                    Bytes.toBytes("-1"));
        }

        put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.ASSIGNED_DATE_BYTE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.CON_DATE_BYTE,
                Bytes.toBytes("-1"));

        datas.add(put);

        hbaseTemplate.saveOrUpdates(HBaseTable.PassTable.TABLE_NAME, datas);

        return true;
    }

    /**
     * 将已使用的 token 记录到文件中
     *
     * @param merchantsId    商户 id
     * @param passTemplateId 优惠券 id
     * @param token          分配的优惠券 token
     * @throws Exception
     */
    private void recordTokenToFile(Integer merchantsId, String passTemplateId,
                                   String token) throws Exception {

        Files.write(
                Paths.get(Commons.TOKEN_DIR, String.valueOf(merchantsId),
                        passTemplateId + Commons.USED_TOKEN_SUFFIX),
                (token + "\n").getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND
        );
    }
}
