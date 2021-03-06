package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商户投放的优惠券 VO
 *
 * @author lingb
 * @date 2018.11.18 13:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplateVO {
    /**
     * 所属商户 id
     */
    private Integer id;

    /**
     * 优惠券标题
     */
    private String title;

    /**
     * 优惠券摘要
     */
    private String summary;

    /**
     * 优惠券详细信息
     */
    private String desc;

    /**
     * 最大个数限制
     */
    private Long limit;

    /**
     * 优惠券是否有 token, 用于商户核销
     * token存储于 Redis Set, 每次领取从 Redis中获取（拿）
     */
    private Boolean hasToken;

    /**
     * 优惠券背景色
     */
    private Integer background;

    /**
     * 优惠券开始时间
     */
    private Date start;

    /**
     * 优惠券结束时间
     */
    private Date end;
}
