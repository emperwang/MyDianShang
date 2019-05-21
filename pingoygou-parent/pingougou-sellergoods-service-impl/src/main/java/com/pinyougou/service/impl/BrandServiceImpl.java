package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
