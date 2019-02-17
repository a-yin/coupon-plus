package com.lingb.couponplus.merchants.vo;

import com.lingb.couponplus.merchants.constant.ResultCodeEnum;
import com.lingb.couponplus.merchants.entity.Merchants;
import com.lingb.couponplus.merchants.repository.MerchantsRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商户对象VO
 *
 * @author lingb
 * @date 2018.11.15 22:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantsVO {

    /**
     * 商户名称
     */
    private String name;

    /**
     * 商户 logo
     */
    private String logoUrl;

    /**
     * 商户营业执照
     */
    private String businessLicenseUrl;

    /**
     * 商户联系电话
     */
    private String phone;

    /**
     * 商户地址
     */
    private String address;

    /**
     * 验证请求的有效性
     *
     * @param merchantsRepository {@link MerchantsRepository}
     * @return {@link ResultCodeEnum}
     *
     */
    public ResultCodeEnum validate(MerchantsRepository merchantsRepository) {

        if (merchantsRepository.findByName(this.name) != null) {
            return ResultCodeEnum.DUPLICATE_NAME;
        }

        if (null == this.logoUrl) {
            return ResultCodeEnum.EMPTY_LOGO;
        }

        if (null == this.businessLicenseUrl) {
            return ResultCodeEnum.EMPTY_BUSINESS_LICENSE;
        }

        if (null == this.address) {
            return ResultCodeEnum.EMPTY_ADDRESS;
        }

        if (null == this.phone) {
            return ResultCodeEnum.ERROR_PHONE;
        }

        return ResultCodeEnum.SUCCESS;
    }

    /**
     * 将 MerchantsVO 转换为 Merchants
     *
     * @return {@link Merchants}
     */
    public Merchants toMerchants() {

        Merchants merchants = new Merchants();

        merchants.setName(name);
        merchants.setLogoUrl(logoUrl);
        merchants.setBusinessLicenseUrl(businessLicenseUrl);
        merchants.setPhone(phone);
        merchants.setAddress(address);

        return merchants;
    }
}
