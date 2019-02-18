package com.lingb.couponplus.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.constant.PassStatusEnum;
import com.lingb.couponplus.passbook.entity.Merchant;
import com.lingb.couponplus.passbook.mapper.PassRowMapper;
import com.lingb.couponplus.passbook.repository.MerchantRepository;
import com.lingb.couponplus.passbook.service.IUserPassService;
import com.lingb.couponplus.passbook.vo.PassInfoVO;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import com.lingb.couponplus.passbook.vo.PassVO;
import com.lingb.couponplus.passbook.vo.ResultVO;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户优惠券 Service接口的实现
 *
 * @author lingb
 * @date 2019.02.18 13:11
 */
@Slf4j
@Service
public class UserPassServiceImpl implements IUserPassService{

    /**
     * HBase 客户端
     */
    private final HbaseTemplate hbaseTemplate;

    /**
     * Merchant Dao 接口
     */
    private final MerchantRepository merchantRepository;

    @Autowired
    public UserPassServiceImpl(HbaseTemplate hbaseTemplate, MerchantRepository merchantRepository) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public ResultVO listUserPass(Long userId) throws Exception {
        return listPassByStatus(userId, PassStatusEnum.UNUSED);
    }

    @Override
    public ResultVO listUserUsedPass(Long userId) throws Exception {
        return listPassByStatus(userId, PassStatusEnum.USED);
    }

    @Override
    public ResultVO listUserAllPass(Long userId) throws Exception {
        return listPassByStatus(userId, PassStatusEnum.ALL);
    }

    @Override
    public ResultVO userUsePass(PassVO passVO) {
        // 根据 userId 构造行键前缀
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(
                String.valueOf(passVO.getUserId())).reverse().toString());
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        filters.add(new PrefixFilter(rowPrefix));
        filters.add(new SingleColumnValueFilter(
                HBaseTable.PassTable.FAMILY_I_BYTE,
                HBaseTable.PassTable.TEMPLATE_ID_BYTE,
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(passVO.getTemplateId())
        ));
        filters.add(new SingleColumnValueFilter(
                HBaseTable.PassTable.FAMILY_I_BYTE,
                HBaseTable.PassTable.CON_DATE_BYTE,
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));

        // Scan 设置链式过滤器，过滤器默认是 and 关系 MUST_PASS_ALL
        scan.setFilter(new FilterList(filters));

        List<PassVO> passes = hbaseTemplate.find(HBaseTable.PassTable.TABLE_NAME,
                scan, new PassRowMapper());

        if (null == passes || passes.size() != 1) {
            log.error("UserUsePass Error: {}", JSON.toJSONString(passes));
            return ResultVO.failure("UserUsePass Error");
        }

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(passes.get(0).getRowKey().getBytes());
        put.addColumn(HBaseTable.PassTable.FAMILY_I_BYTE, HBaseTable.PassTable.CON_DATE_BYTE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date()))
        );
        datas.add(put);

        hbaseTemplate.saveOrUpdates(HBaseTable.PassTable.TABLE_NAME, datas);

        return ResultVO.success();
    }

    /**
     * 根据优惠券状态获取优惠券
     * @param userId  用户 id
     * @param passStatus {@link PassStatusEnum} 优惠券状态
     * @return {@link ResultVO}
     * @throws Exception
     */
    private ResultVO listPassByStatus(Long userId, PassStatusEnum passStatus) throws Exception {

        // 根据 userId 构造行键前缀
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());

        CompareFilter.CompareOp compareOp =
                passStatus == PassStatusEnum.UNUSED ?
                        CompareFilter.CompareOp.EQUAL : CompareFilter.CompareOp.NOT_EQUAL;

        Scan scan = new Scan();

        List<Filter> filters = new ArrayList<>();

        // 1. 行键前缀过滤器, 找到特定用户的优惠券
        filters.add(new PrefixFilter(rowPrefix));
        // 2. 基于列单元值的过滤器, 找到未使用的优惠券
        if (passStatus != PassStatusEnum.ALL) {
            filters.add(
                    new SingleColumnValueFilter(
                            HBaseTable.PassTable.FAMILY_I_BYTE,
                            HBaseTable.PassTable.CON_DATE_BYTE, compareOp,
                            Bytes.toBytes("-1"))
            );
        }

        // Scan 设置链式过滤器，过滤器默认是 and 关系 MUST_PASS_ALL
        scan.setFilter(new FilterList(filters));

        List<PassVO> passVOs = hbaseTemplate.find(HBaseTable.PassTable.TABLE_NAME, scan, new PassRowMapper());
        Map<String, PassTemplateVO> passTemplateMap = buildPassTemplateMap(passVOs);
        Map<Integer, Merchant> merchantMap = buildMerchantMap(
                new ArrayList<>(passTemplateMap.values()));

        List<PassInfoVO> result = new ArrayList<>();

        for (PassVO pass : passVOs) {
            PassTemplateVO passTemplate = passTemplateMap.getOrDefault(
                    pass.getTemplateId(), null);
            if (null == passTemplate) {
                log.error("PassTemplate Null : {}", pass.getTemplateId());
                continue;
            }

            Merchant merchant = merchantMap.getOrDefault(passTemplate.getId(), null);
            if (null == merchant) {
                log.error("Merchants Null : {}", passTemplate.getId());
                continue;
            }

            result.add(new PassInfoVO(pass, passTemplate, merchant));
        }

        return new ResultVO(result);
    }


    /**
     * 通过获取的 passVOs 构造 Map
     *
     * @param passVOs {@link PassVO}
     * @return Map {@link PassTemplateVO}
     */
    private Map<String, PassTemplateVO> buildPassTemplateMap(List<PassVO> passVOs) throws IOException, ParseException {
        List<String> templateIds = passVOs.stream().map(PassVO::getTemplateId)
                .collect(Collectors.toList());

        List<Get> templateGets = new ArrayList<>(templateIds.size());
        templateIds.forEach(t -> templateGets.add(new Get(t.getBytes())));

        Result[] templateResults = hbaseTemplate.getConnection()
                .getTable(TableName.valueOf(HBaseTable.PassTemplateTable.TABLE_NAME))
                .get(templateGets);

        String[] patterns = new String[] {"yyyy-MM-dd"};

        // 构造 PassTemplateId -> PassTemplate Object 的 Map, 用于构造 PassInfo
        Map<String, PassTemplateVO> templateId2Object = new HashMap<>();
        for (Result item : templateResults) {
            PassTemplateVO passTemplateVO = new PassTemplateVO();

            // 基本信息
            passTemplateVO.setId(Bytes.toInt(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.ID_BYTE)));
            passTemplateVO.setTitle(Bytes.toString(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.TITLE_BYTE)));
            passTemplateVO.setSummary(Bytes.toString(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.SUMMARY_BYTE)));
            passTemplateVO.setDesc(Bytes.toString(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.DESC_BYTE)));
            passTemplateVO.setHasToken(Bytes.toBoolean(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.HAS_TOKEN_BYTE)));
            passTemplateVO.setBackground(Bytes.toInt(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_B_BYTE, HBaseTable.PassTemplateTable.BACKGROUND_BYTE)));

            // 约束信息
            passTemplateVO.setLimit(Bytes.toLong(
                    item.getValue(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.LIMIT_BYTE)));
            passTemplateVO.setStart(DateUtils.parseDate(
                    Bytes.toString(item.getValue(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.START_BYTE))
                    , patterns));
            passTemplateVO.setEnd(DateUtils.parseDate(
                    Bytes.toString(item.getValue(HBaseTable.PassTemplateTable.FAMILY_C_BYTE, HBaseTable.PassTemplateTable.END_BYTE))
                    , patterns));

            templateId2Object.put(Bytes.toString(item.getRow()), passTemplateVO);
        }

        return templateId2Object;
    }

    /**
     * 通过获取的 PassTemplate 构造 Merchant Map
     *
     * @param passTemplates {@link PassTemplate}
     * @return {@link Merchant}
     */
    private
    Map<Integer, Merchant> buildMerchantMap(List<PassTemplateVO> passTemplateVOs) {

        Map<Integer, Merchant> merchantMap = new HashMap<>();
        List<Integer> merchantsIds = passTemplateVOs.stream().map(
                PassTemplateVO::getId
        ).collect(Collectors.toList());
        List<Merchant> merchants = merchantRepository.listByIdIn(merchantsIds);

        merchants.forEach(m -> merchantMap.put(m.getId(), m));

        return merchantMap;
    }
}
