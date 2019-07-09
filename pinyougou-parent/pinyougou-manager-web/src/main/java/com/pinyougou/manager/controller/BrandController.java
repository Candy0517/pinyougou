package com.pinyougou.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {

        return brandService.findAll();

    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page, @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        PageResult pageResult = brandService.findPage(page, size);

        return pageResult;
    }

    @RequestMapping("/save")
    public Result save(@RequestBody TbBrand tbBrand) {


        if (tbBrand.getId() == null) {
            try {
                brandService.add(tbBrand);
                return new Result(true, "保存成功！");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "保存失败！");
            }
        } else {
            try {
                brandService.update(tbBrand);
                return new Result(true, "修改成功！");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "修改失败！");
            }
        }

    }

    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){

      return   brandService.findOne(id);
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam(name = "ids")Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, "删除失败！");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand tbBrand, @RequestParam(name = "page",required = false,defaultValue = "1") Integer page,@RequestParam(name = "size",required = false,defaultValue = "10") Integer size){

        PageResult pageResult = brandService.findPage(tbBrand, page, size);

        return pageResult;
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        List<Map> list= brandService.selectOptionList();
        return list;
    }
}
