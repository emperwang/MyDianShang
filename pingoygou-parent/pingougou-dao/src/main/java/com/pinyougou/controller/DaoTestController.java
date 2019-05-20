package com.pinyougou.controller;

import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DaoTestController {

    @Autowired
    private TbBrandMapper mapper;

    @RequestMapping("/findall")
    public List<TbBrand> findAll(){
        List<TbBrand> tbBrands = mapper.selectByExample(null);
        return tbBrands;
    }
}
