package com.lingb.couponplus.merchants.controller;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.merchants.service.IMerchantsService;
import com.lingb.couponplus.merchants.vo.MerchantsVO;
import com.lingb.couponplus.merchants.vo.PassTemplateVO;
import com.lingb.couponplus.merchants.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Merchants 控制层
 *
 * @author lingb
 * @date 2018.11.17 15:46
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsController {

    /**
     * 商户业务接口
     */
    @Autowired
    private  IMerchantsService merchantsService;

    @ResponseBody
    @PostMapping("/create")
    public ResultVO createMerchants(@RequestBody MerchantsVO merchantsVO) {
        log.info("CreateMerchants: {}", JSON.toJSONString(merchantsVO));
        return merchantsService.createMerchants(merchantsVO);
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
