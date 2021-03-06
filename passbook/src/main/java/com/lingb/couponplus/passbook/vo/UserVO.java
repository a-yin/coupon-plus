package com.lingb.couponplus.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 VO
 *
 * @author lingb
 * @date 2018.11.18 19:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户基本信息
     */
    private BaseInfo baseInfo;

    /**
     * 用户额外信息
     */
    private OtherInfo otherInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseInfo {
        private String name;
        private Integer age;
        private String sex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherInfo {
        private String phone;
        private String address;
    }
}
