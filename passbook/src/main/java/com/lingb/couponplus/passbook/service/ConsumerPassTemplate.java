package com.lingb.couponplus.passbook.service;

import com.alibaba.fastjson.JSON;
import com.lingb.couponplus.passbook.constant.Commons;
import com.lingb.couponplus.passbook.vo.PassTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 接收Kafka 中 PassTemplate
 *
 * @author lingb
 * @date 2018.11.19 10:39
 */
@Slf4j
@Component
public class ConsumerPassTemplate {

    @Autowired
    private IHBasePassService hBasePassService;

    /**
     * Consumer消费者，即接收（消费） Kafka 的消息
     *
     * @param passTemplate 商家投放的优惠券
     * @param key  消息key
     * @param partition  分区 id
     * @param topic  主题
     */
    @KafkaListener(topics = {Commons.TEMPLATE_TOPIC})  // @KafkaListener Kafka监听器，该方法自动接收商户投放的优惠券
    public void receive(@Payload String passTemplate,
                        @Header(KafkaHeaders.MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.PARTITION_ID) int partition,
                        @Header(KafkaHeaders.TOPIC) String topic) {
        log.info("Consumer receive passTemplate: {}", passTemplate);
        PassTemplateVO passTemplateVO;
        try {
            passTemplateVO = JSON.parseObject(passTemplate, PassTemplateVO.class);
        } catch (Exception e) {
            log.error("Parse passTemplate error: {}", e.getMessage());
            return;
        }

        // 将商户投放的优惠券存储到HBase 中
        log.info("DropPassTemplateToHBase: {}",
                hBasePassService.dropPassTemplateToHBase(passTemplateVO));
    }
}
