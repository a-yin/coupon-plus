package com.lingb.couponplus.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.merchant.constant.Commons;
import com.lingb.couponplus.merchant.constant.ResultCodeEnum;
import com.lingb.couponplus.merchant.entity.Merchant;
import com.lingb.couponplus.merchant.repository.MerchantRepository;
import com.lingb.couponplus.merchant.service.IMerchantService;
import com.lingb.couponplus.merchant.vo.MerchantVO;
import com.lingb.couponplus.merchant.vo.PassTemplateVO;
import com.lingb.couponplus.merchant.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

/**
 * IMerchantService 接口的实现类
 *
 * @author lingb
 * @date 2018.11.16 22:38
 */
@Slf4j
@Service
public class MerchantServiceImpl implements IMerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public ResultVO createMerchants(MerchantVO merchantVO) {
        ResultVO resultVO = new ResultVO();
        ResultCodeEnum errorCode = merchantVO.validate(merchantRepository);
        if (ResultCodeEnum.SUCCESS != errorCode) {
            resultVO.setCode(errorCode.getCode());
            resultVO.setMsg(errorCode.getDesc());
            resultVO.setData(-1);

        } else {
            Merchant merchant = merchantRepository.save(merchantVO.toMerchants());
            resultVO.setData(merchant.getId());
        }

        return resultVO;
    }

    @Override
    public ResultVO getMerchantsById(Integer id) {

        ResultVO resultVO = new ResultVO();
        Merchant merchant = merchantRepository.findById(id);
        if (null == merchant) {
            resultVO.setCode(ResultCodeEnum.MERCHANTS_NOT_EXIST.getCode());
            resultVO.setMsg(ResultCodeEnum.MERCHANTS_NOT_EXIST.getDesc());
        }

        resultVO.setData(merchant);
        return resultVO;
    }

    @Override
    public ResultVO dropPassTemplate(PassTemplateVO passTemplateVO) {
        ResultVO resultVO = new ResultVO();
        ResultCodeEnum resultCode = passTemplateVO.validate(merchantRepository);
        if (resultCode != ResultCodeEnum.SUCCESS) {
            resultVO.setCode(resultCode.getCode());
            resultVO.setMsg(resultCode.getDesc());

        } else {
            byte[] bytes = JSON.toJSONString(passTemplateVO).getBytes();
            String passTemplate = null;
            try {
                passTemplate = new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // Kafka producer
            kafkaTemplate.send(
                    Commons.TEMPLATE_TOPIC,
                    Commons.TEMPLATE_TOPIC,
                    passTemplate
            );
            log.info("DropPassTemplates: {}", passTemplate);
        }

        return resultVO;
    }
}
