package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.service.BrandService;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = BrandService.class)
@Component
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper mapper;

    @Override
    public List<TbBrand> findAll() {
        List<TbBrand> tbBrands = mapper.selectByExample(null);
        return tbBrands;
    }

    @Override
    public PageResult findByPage(Integer page, Integer pagesize) {
        PageHelper.startPage(page,pagesize);
        List<TbBrand> results = mapper.selectByExample(null);
        PageInfo<TbBrand> pageInfo = new PageInfo<>(results);
        List<TbBrand> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    @Override
    public int add(TbBrand brand) {
        int insert = mapper.insert(brand);
        return insert;
    }

    @Override
    public int updateBrand(TbBrand brand) {
        int i = mapper.updateByPrimaryKey(brand);
        return i;
    }

    @Override
    public TbBrand findById(Integer id) {
        TbBrand tbBrand = mapper.selectByPrimaryKey(Long.parseLong(id.toString()));
        return tbBrand;
    }

    @Override
    public int deletes(Long[] ids) {
        int res = 0;
        for (Long id : ids) {
            int i = mapper.deleteByPrimaryKey(id);
            res += i;
        }
        return res;
    }

    @Override
    public PageResult search(TbBrand brand,Integer page,Integer pagesize) {
        PageHelper.startPage(page,pagesize);
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        if (brand != null){
            if (brand.getName() != null && brand.getName().length()>0){
                criteria.andNameLike("%"+brand.getName()+"%");
            }
            if (brand.getFirstChar() != null && brand.getFirstChar().length() >0){
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
        }
        List<TbBrand> results = mapper.selectByExample(example);
        PageInfo<TbBrand> pageInfo = new PageInfo<>(results);
        List<TbBrand> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }
}
