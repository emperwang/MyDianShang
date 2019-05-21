package com.pinyougou.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import com.pinyougou.viewEntity.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService service;

    @RequestMapping("/findAll.do")
    @ResponseBody
    public List<TbBrand> findAll(){
        List<TbBrand> all = service.findAll();
        return all;
    }

    @RequestMapping("/findPage.do")
    @ResponseBody
    public PageResult findPage(@RequestParam(name = "page",required = true) Integer page,@RequestParam(name = "size",required = true) Integer size){
        PageResult byPage = service.findByPage(page, size);
        return byPage;
    }

}
