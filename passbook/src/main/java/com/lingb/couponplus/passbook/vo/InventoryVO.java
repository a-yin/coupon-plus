package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 优惠券库存 VO
 *
 * @author lingb
 * @date 2019.02.18 22:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryVO {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 优惠券模板信息
     */
    private List<PassTemplateInfoVO> passTemplateInfoVOs;
}
