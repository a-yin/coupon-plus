package com.lingb.couponplus.merchant.controller;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.merchant.service.IMerchantService;
import com.lingb.couponplus.merchant.vo.MerchantVO;
import com.lingb.couponplus.merchant.vo.PassTemplateVO;
import com.lingb.couponplus.merchant.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Merchant 控制层
 *
 * @author lingb
 * @date 2018.11.17 15:46
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantController {

    /**
     * 商户 Service接口
     */
    @Autowired
    private IMerchantService merchantsService;

    @ResponseBody
    @PostMapping("/create")
    public ResultVO createMerchants(@RequestBody MerchantVO merchantVO) {
        log.info("CreateMerchants: {}", JSON.toJSONString(merchantVO));
        return merchantsService.createMerchants(merchantVO);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResultVO getMerchantsInfo(@PathVariable Integer id) {
        log.info("getMerchantsInfo: {}", id);
        return merchantsService.getMerchantsById(id);
    }

    @ResponseBody
    @PostMapping("/drop")
    public ResultVO dropPassTemplate(@RequestBody PassTemplateVO passTemplateVO) {
        log.info("DropPassTemplate: {}", passTemplateVO);
        return merchantsService.dropPassTemplate(passTemplateVO);
    }
}
