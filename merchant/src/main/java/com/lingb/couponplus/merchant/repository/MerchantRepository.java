package com.lingb.couponplus.merchant.repository;

import com.lingb.couponplus.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商户 Dao接口
 *
 * @author lingb
 * @date 2018.11.13 18:44
 */
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    /**
     * 根据商户 id 获取商户对象
     *
     * @param id 商户 id
     * @return {@link Merchant}
     */
    Merchant findById(Integer id);

    /**
     * 根据商户名 获取商户对象
     *
     * @param name 商户名称
     * @return {@link Merchant}
     */
    Merchant findByName(String name);

    /**
     * 根据商户 ids 获取商户对象
     *
     * @param ids 商户 ids
     * @return {@link Merchant}
     */
//    List<Merchant> listByIds(List<Integer> ids);
}
