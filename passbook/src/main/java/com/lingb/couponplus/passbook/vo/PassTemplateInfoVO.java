package com.lingb.couponplus.passbook.vo;

import com.lingb.couponplus.passbook.entity.Merchant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 优惠券模板信息VO
 *
 * @author lingb
 * @date 2019.02.18 22:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplateInfoVO {

    /**
     * 优惠券模板
     */
    private PassTemplateVO passTemplateVO;

    /**
     * 优惠券所属的商户
     */
    private Merchant merchant;
}
