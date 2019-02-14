package com.lingb.couponplus.passbook.dao;

import com.lingb.couponplus.passbook.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商户接口
 *
 * @author lingb
 * @date 2019.02.13 18:44
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {
    /**
     * 根据id 获取商户对象
     *
     * @param id
     * @return
     */
    Merchants findById(Integer id);

    /**
     * 根据商户名 获取商户对象
     *
     * @param name
     * @return
     */
    Merchants findByName(String name);

    /**
     * 根据商户 ids 获取商户对象
     *
     * @param ids
     * @return
     */
    List<Merchants> listByIds(List<Integer> ids);
}
