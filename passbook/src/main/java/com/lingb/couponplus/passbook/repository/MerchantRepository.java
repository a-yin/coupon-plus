package com.lingb.couponplus.passbook.repository;

import com.lingb.couponplus.passbook.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
    Merchant getById(Integer id);

    /**
     * 根据商户名 获取商户对象
     *
     * @param name 商户名称
     * @return {@link Merchant}
     */
    Merchant getByName(String name);

    /**
     * 根据商户 ids 获取商户对象
     *
     * @param ids 商户 ids
     * @return {@link Merchant}
     */
    List<Merchant> listByIdIn(List<Integer> ids);
}
