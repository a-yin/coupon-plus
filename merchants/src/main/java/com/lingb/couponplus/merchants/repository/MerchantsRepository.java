package com.lingb.couponplus.merchants.repository;

import com.lingb.couponplus.merchants.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商户 Dao接口
 *
 * @author lingb
 * @date 2018.11.13 18:44
 */
public interface MerchantsRepository extends JpaRepository<Merchants, Integer> {
    /**
     * 根据商户 id 获取商户对象
     *
     * @param id 商户 id
     * @return {@link Merchants}
     */
    Merchants findById(Integer id);

    /**
     * 根据商户名 获取商户对象
     *
     * @param name 商户名称
     * @return {@link Merchants}
     */
    Merchants findByName(String name);

    /**
     * 根据商户 ids 获取商户对象
     *
     * @param ids 商户 ids
     * @return {@link Merchants}
     */
//    List<Merchants> listByIds(List<Integer> ids);
}
