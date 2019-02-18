package com.lingb.couponplus.merchant.vo;

import com.lingb.couponplus.merchant.constant.ResultCodeEnum;
import com.lingb.couponplus.merchant.entity.Merchant;
import com.lingb.couponplus.merchant.repository.MerchantRepository;
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
public class MerchantVO {

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
     * @param merchantRepository {@link MerchantRepository}
     * @return {@link ResultCodeEnum}
     *
     */
    public ResultCodeEnum validate(MerchantRepository merchantRepository) {

        if (merchantRepository.findByName(this.name) != null) {
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
     * 将 MerchantVO 转换为 Merchant
     *
     * @return {@link Merchant}
     */
    public Merchant toMerchants() {

        Merchant merchant = new Merchant();

        merchant.setName(name);
        merchant.setLogoUrl(logoUrl);
        merchant.setBusinessLicenseUrl(businessLicenseUrl);
        merchant.setPhone(phone);
        merchant.setAddress(address);

        return merchant;
    }
}
