package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌接口
 */
public interface BrandService {

    List<TbBrand> findAll();


    PageResult findPage(Integer page, Integer size);

    void add(TbBrand tbBrand);

    void update(TbBrand tbBrand);

    TbBrand findOne(Long id);

    void delete(Long[] ids);
    PageResult findPage(TbBrand tbBrand,Integer page, Integer size);

    List<Map> selectOptionList();

}
