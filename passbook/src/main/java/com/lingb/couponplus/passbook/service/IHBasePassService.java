package com.lingb.couponplus.passbook.service;

import com.lingb.couponplus.passbook.vo.PassTemplateVO;

/**
 * HBasePass Service接口
 *
 * @author lingb
 * @date 2018.11.18 10:43
 */
public interface IHBasePassService {

    /**
     * 将 PassTemplate 写入 HBase
     * @param passTemplate {@link com.lingb.couponplus.passbook.vo.PassTemplateVO}
     * @return true/false
     * */
    boolean dropPassTemplateToHBase(PassTemplateVO passTemplateVO);
}
