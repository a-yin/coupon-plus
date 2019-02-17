package com.lingb.couponplus.merchants.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.merchants.constant.Commons;
import com.lingb.couponplus.merchants.constant.ResultCodeEnum;
import com.lingb.couponplus.merchants.entity.Merchants;
import com.lingb.couponplus.merchants.repository.MerchantsRepository;
import com.lingb.couponplus.merchants.service.IMerchantsService;
import com.lingb.couponplus.merchants.vo.MerchantsVO;
import com.lingb.couponplus.merchants.vo.PassTemplateVO;
import com.lingb.couponplus.merchants.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

/**
 * IMerchantsService 接口的实现类
 *
 * @author lingb
 * @date 2018.11.16 22:38
 */
@Slf4j
@Service
public class MerchantsServiceImpl implements IMerchantsService {

    @Autowired
    private MerchantsRepository merchantsRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public ResultVO createMerchants(MerchantsVO merchantsVO) {
        ResultVO resultVO = new ResultVO();
        ResultCodeEnum errorCode = merchantsVO.validate(merchantsRepository);
        if (ResultCodeEnum.SUCCESS != errorCode) {
            resultVO.setCode(errorCode.getCode());
            resultVO.setMsg(errorCode.getDesc());
            resultVO.setData(-1);

        } else {
            Merchants merchants = merchantsRepository.save(merchantsVO.toMerchants());
            resultVO.setData(merchants.getId());
        }

        return resultVO;
    }

    @Override
    public ResultVO getMerchantsById(Integer id) {

        ResultVO resultVO = new ResultVO();
        Merchants merchants = merchantsRepository.findById(id);
        if (null == merchants) {
            resultVO.setCode(ResultCodeEnum.MERCHANTS_NOT_EXIST.getCode());
            resultVO.setMsg(ResultCodeEnum.MERCHANTS_NOT_EXIST.getDesc());
        }

        resultVO.setData(merchants);
        return resultVO;
    }

    @Override
    public ResultVO dropPassTemplate(PassTemplateVO passTemplateVO) {
        ResultVO resultVO = new ResultVO();
        ResultCodeEnum resultCode = passTemplateVO.validate(merchantsRepository);
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
