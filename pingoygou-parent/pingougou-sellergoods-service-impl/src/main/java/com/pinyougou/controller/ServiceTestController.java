package com.pinyougou.controller;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceTestController {
    @Autowired
    private BrandServiceImpl service;

    @RequestMapping("findAll")
    public List<TbBrand> findAll(){
        List<TbBrand> all = service.findAll();
        return all;
    }
}
