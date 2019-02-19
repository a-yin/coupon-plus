package com.lingb.couponplus.passbook.service.impl;

import com.lingb.couponplus.passbook.constant.HBaseTable;
import com.lingb.couponplus.passbook.mapper.PassTemplateRowMapper;
import com.lingb.couponplus.passbook.repository.MerchantRepository;
import com.lingb.couponplus.passbook.service.IInventoryService;
import com.lingb.couponplus.passbook.service.IUserPassService;
import com.lingb.couponplus.passbook.util.RowKeyGenUtil;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import com.lingb.couponplus.passbook.vo.ResultVO;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 优惠券库存 Service接口的实现
 *
 * @author lingb
 * @date 2018.11.21 21:57
 */
@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService{

    /**
     * HBase 客户端
     */
    private final HbaseTemplate hbaseTemplate;

    /**
     * Merchants Dao 接口
     */
    private final MerchantRepository merchantRepository;

    /**
     * 用户优惠券 Service 接口
     */
    private final IUserPassService userPassService;

    @Autowired
    public InventoryServiceImpl(HbaseTemplate hbaseTemplate,
                                MerchantRepository merchantRepository,
                                IUserPassService userPassService) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantRepository = merchantRepository;
        this.userPassService = userPassService;
    }

    @Override
    public ResultVO listInventory(Long userId) throws Exception {
        return null;
    }

    /**
     * 获取系统中可用的优惠券：
     * 1.创建FilterList 制定过滤规则
     * 2.将FilterList 加入到Scan, HBaseTemplate 查找符合过滤条件的（有效）优惠券
     * 3.for 循环排除如下的优惠券，即：
     *      用户已经领取了
     *      优惠券过期了
     * 4.返回系统可用的优惠券
     *
     * 思考：若优惠券数量过多，达到了成千上万张，可考虑分页、缓存等等处理的解决方案！！
     *
     * @param excludeIds 需要排除的优惠券 ids
     * @return {@link PassTemplate}
     */
    private List<PassTemplateVO> listAvailablePassTemplate(List<String> excludeIds) {

        // 过滤器是 or 关系  MUST_PASS_ONE
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);

        // 库存 > 0
        filterList.addFilter(
                new SingleColumnValueFilter(
                        HBaseTable.PassTemplateTable.FAMILY_C_BYTE,
                        HBaseTable.PassTemplateTable.LIMIT_BYTE,
                        CompareFilter.CompareOp.GREATER,
                        new LongComparator(0L)
                )
        );

        // 未被使用 -1
        filterList.addFilter(
                new SingleColumnValueFilter(
                        HBaseTable.PassTemplateTable.FAMILY_C_BYTE,
                        HBaseTable.PassTemplateTable.LIMIT_BYTE,
                        CompareFilter.CompareOp.EQUAL,
                        Bytes.toBytes("-1")
                )
        );

        Scan scan = new Scan();
        scan.setFilter(filterList);

        List<PassTemplateVO> validTemplates = hbaseTemplate.find(
                HBaseTable.PassTemplateTable.TABLE_NAME, scan, new PassTemplateRowMapper());
        List<PassTemplateVO> availablePassTemplates = new ArrayList<>();

        Date now = new Date();

        for (PassTemplateVO validTemplate : validTemplates) {

            if (excludeIds.contains(RowKeyGenUtil.genPassTemplateRowKey(validTemplate))) {
                continue;
            }

            if (now.getTime() >= validTemplate.getStart().getTime()
                    && now.getTime() <= validTemplate.getEnd().getTime()) {
                availablePassTemplates.add(validTemplate);
            }
        }

        return availablePassTemplates;
    }



}
