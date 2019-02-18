package com.lingb.couponplus.passbook.vo;

import com.lingb.couponplus.passbook.entity.Merchant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户优惠券的信息 VO
 *
 * @author lingb
 * @date 2019.02.18 13:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassInfoVO {
    /**
     * 优惠券
     */
    private PassVO passVO;

    /**
     * 优惠券模板
     */
    private PassTemplateVO passTemplateVO;

    /**
     * 优惠券所属的商户
     */
    private Merchant merchant;
}
