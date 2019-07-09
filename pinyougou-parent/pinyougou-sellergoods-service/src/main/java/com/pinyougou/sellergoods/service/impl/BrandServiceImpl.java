package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public List<TbBrand> findAll() {

        return tbBrandMapper.selectByExample(null);

    }

    @Override
    public PageResult findPage(Integer page, Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 5;
        }

        PageHelper.startPage(page, size);

        Page<TbBrand> tbBrands = (Page<TbBrand>) tbBrandMapper.selectByExample(null);

        PageResult pageResult = new PageResult(tbBrands.getTotal(), tbBrands.getResult());

        return pageResult;
    }

    @Override
    public void add(TbBrand tbBrand) {
        tbBrandMapper.insertSelective(tbBrand);
    }

    @Override
    public void update(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long[] ids) {

        for (Long id : ids) {

            tbBrandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPage(TbBrand tbBrand, Integer page, Integer size) {

        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        PageHelper.startPage(page, size);
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        if (tbBrand != null) {
            if (tbBrand.getName() != null && tbBrand.getName().length() > 0) {
                criteria.andNameLike("%" + tbBrand.getName() + "%");
            }
            if (tbBrand.getFirstChar() != null && tbBrand.getFirstChar().length() == 1) {
                criteria.andFirstCharLike(tbBrand.getFirstChar() );
            }

        }


        Page<TbBrand> tbBrands = (Page<TbBrand>) tbBrandMapper.selectByExample(example);

        PageResult pageResult = new PageResult(tbBrands.getTotal(), tbBrands.getResult());

        return pageResult;
    }

    @Override
    public List<Map> selectOptionList() {

        List<Map> maps = new ArrayList<>();

        List<TbBrand> tbBrands = tbBrandMapper.selectByExample(null);
        for (TbBrand tbBrand : tbBrands) {
            if (tbBrand.getName()==null){continue;}
            Map map=new HashMap();
            map.put("id",tbBrand.getId());
            map.put("text",tbBrand.getName());
            maps.add(map);
        }

        return maps;
    }

}
