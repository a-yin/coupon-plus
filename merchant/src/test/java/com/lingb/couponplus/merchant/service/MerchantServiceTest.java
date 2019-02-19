package com.lingb.couponplus.merchant.service;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.merchant.vo.MerchantVO;
import com.lingb.couponplus.merchant.vo.PassTemplateVO;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 商户业务测试类
 *
 * @author lingb
 * @date 2018.11.16 22:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantServiceTest {

    @Autowired
    private IMerchantService merchantsService;

    @Test
    @Transactional
    public void testCreateMerchants() {
        MerchantVO merchantVO = new MerchantVO(
                "湛江甲鸟",
                "www.lingb.com",
                "www.lingb.com",
                "1234567890",
                "湛江"
        );

        System.out.print(JSON.toJSON(merchantsService.createMerchants(merchantVO)));
    }

    @Test
    public void testBuildMerchantsInfoById() {

        System.out.println(JSON.toJSONString(merchantsService.getMerchantsById(3)));
    }

    @Test
    public void testDropPassTemplate() {

        PassTemplateVO passTemplateVO = new PassTemplateVO();
        passTemplateVO.setId(3);
        passTemplateVO.setTitle("湛江甲鸟3");
        passTemplateVO.setSummary("简介: 甲鸟");
        passTemplateVO.setDesc("详情: 湛江甲鸟");
        passTemplateVO.setLimit(100L);
        passTemplateVO.setHasToken(false);
        passTemplateVO.setBackground(2);
        passTemplateVO.setStart(DateUtils.addDays(new Date(), -10));
        passTemplateVO.setEnd(DateUtils.addDays(new Date(), 10));

        System.out.println(JSON.toJSONString(
                merchantsService.dropPassTemplate(passTemplateVO)
        ));
    }
}
