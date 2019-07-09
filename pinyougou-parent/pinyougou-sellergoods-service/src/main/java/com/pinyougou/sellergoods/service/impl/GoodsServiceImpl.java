package com.pinyougou.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbBrandMapper brandMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbSellerMapper sellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);


        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    @Transactional
    public void add(Goods goods) {

        TbGoods tbGoods = goods.getGoods();
        tbGoods.setAuditStatus("0");
        tbGoods.setIsMarketable("1");
        goodsMapper.insert(tbGoods);//存入商品表

        TbGoodsDesc goodsDesc = goods.getGoodsDesc();
        goodsDesc.setGoodsId(tbGoods.getId());

        goodsDescMapper.insert(goodsDesc);//存入商品扩展数据


        saveItemList(goods);
        /*if ("1".equals(goods.getGoods().getIsEnableSpec())) {
            List<TbItem> itemList = goods.getItemList();

            for (TbItem tbItem : itemList) {

                String title = goods.getGoods().getGoodsName();//标题

                Map<String, Object> specMap = JSON.parseObject(tbItem.getSpec());

                for (String key : specMap.keySet()) {

                    title = "" + specMap.get(key);
                }
                tbItem.setTitle(title);

               *//* tbItem.setGoodsId(goods.getGoods().getId());//商品SPU编号
                tbItem.setSellerId(goods.getGoods().getSellerId());//商家编号
                tbItem.setCategoryid(goods.getGoods().getCategory3Id());//商家分类编号3级
                tbItem.setCreateTime(new Date());//创建时间
                tbItem.setUpdateTime(new Date());//修改时间

                //品牌名称
                TbBrand tbBrand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
                tbItem.setBrand(tbBrand.getName());
                //分类名称
                TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
                tbItem.setCategory(tbItemCat.getName());
                //分类名称
                TbSeller tbSeller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
                tbItem.setSeller(tbSeller.getNickName());
                //图片地址（取spu的第一个图片）
                List<Map> list = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
                if (list.size() > 0) {
                    tbItem.setImage((String) list.get(0).get("url"));
                }*//*

                setItemValus(goods,tbItem);

                itemMapper.insert(tbItem);


            }
        } else {
            TbItem tbItem = new TbItem();
            tbItem.setTitle(goods.getGoods().getGoodsName());//标题
            tbItem.setPrice(goods.getGoods().getPrice());//价格
            tbItem.setNum(888);//库存
            tbItem.setStatus("1");//状态
            tbItem.setIsDefault("1");//默认
            tbItem.setSpec("{}");//规格
            setItemValus(goods,tbItem);

            itemMapper.insert(tbItem);
        }
*/

    }

    private void setItemValus(Goods goods,TbItem tbItem){
        tbItem.setGoodsId(goods.getGoods().getId());//商品SPU编号
        tbItem.setSellerId(goods.getGoods().getSellerId());//商家编号
        tbItem.setCategoryid(goods.getGoods().getCategory3Id());//商家分类编号3级
        tbItem.setCreateTime(new Date());//创建时间
        tbItem.setUpdateTime(new Date());//修改时间

        //品牌名称
        TbBrand tbBrand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        tbItem.setBrand(tbBrand.getName());
        //分类名称
        TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
        tbItem.setCategory(tbItemCat.getName());
        //分类名称
        TbSeller tbSeller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
        tbItem.setSeller(tbSeller.getNickName());
        //图片地址（取spu的第一个图片）
        List<Map> list = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (list.size() > 0) {
            tbItem.setImage((String) list.get(0).get("url"));
        }

    }

    private void saveItemList(Goods goods){
        if ("1".equals(goods.getGoods().getIsEnableSpec())) {
            List<TbItem> itemList = goods.getItemList();

            for (TbItem tbItem : itemList) {

                String title = goods.getGoods().getGoodsName();//标题

                Map<String, Object> specMap = JSON.parseObject(tbItem.getSpec());

                for (String key : specMap.keySet()) {

                    title = "" + specMap.get(key);
                }
                tbItem.setTitle(title);

               /* tbItem.setGoodsId(goods.getGoods().getId());//商品SPU编号
                tbItem.setSellerId(goods.getGoods().getSellerId());//商家编号
                tbItem.setCategoryid(goods.getGoods().getCategory3Id());//商家分类编号3级
                tbItem.setCreateTime(new Date());//创建时间
                tbItem.setUpdateTime(new Date());//修改时间

                //品牌名称
                TbBrand tbBrand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
                tbItem.setBrand(tbBrand.getName());
                //分类名称
                TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
                tbItem.setCategory(tbItemCat.getName());
                //分类名称
                TbSeller tbSeller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
                tbItem.setSeller(tbSeller.getNickName());
                //图片地址（取spu的第一个图片）
                List<Map> list = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
                if (list.size() > 0) {
                    tbItem.setImage((String) list.get(0).get("url"));
                }*/

                setItemValus(goods,tbItem);

                itemMapper.insert(tbItem);


            }
        } else {
            TbItem tbItem = new TbItem();
            tbItem.setTitle(goods.getGoods().getGoodsName());//标题
            tbItem.setPrice(goods.getGoods().getPrice());//价格
            tbItem.setNum(888);//库存
            tbItem.setStatus("1");//状态
            tbItem.setIsDefault("1");//默认
            tbItem.setSpec("{}");//规格
            setItemValus(goods,tbItem);

            itemMapper.insert(tbItem);
        }
    }


    /**
     * 修改
     */
    @Override
    @Transactional
    public void update(Goods goods) {

        TbGoods tbGoods = goods.getGoods();
        goodsMapper.updateByPrimaryKey(tbGoods);
        TbGoodsDesc goodsDesc = goods.getGoodsDesc();
        goodsDescMapper.updateByPrimaryKey(goodsDesc);

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(tbGoods.getId());

        itemMapper.deleteByExample(example);

        saveItemList(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {

        Goods goods = new Goods();

        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);

        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);

        goods.setItemList(tbItems);

        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete("1");
            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeleteIsNull();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                /*criteria.andSellerIdLike("%" + goods.getSellerId() + "%");*/
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);

            tbGoods.setAuditStatus(status);

            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public void updateMarketable(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);

            tbGoods.setIsMarketable(status);

            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

}
