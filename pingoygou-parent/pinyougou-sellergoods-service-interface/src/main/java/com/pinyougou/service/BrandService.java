package com.pinyougou.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.viewEntity.PageResult;

import java.util.List;

public interface BrandService {
    List<TbBrand> findAll();
    PageResult findByPage(Integer page,Integer pagesize);
}
