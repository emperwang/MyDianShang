package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
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
}
