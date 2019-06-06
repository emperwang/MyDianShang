package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class ItemSearchController {

    @Reference
    private ItemSearchService searchService;

    @PostMapping("itemSearch.do")
    public Map itemSearch(@RequestBody Map keyWords){
        Map map = searchService.itemSearch(keyWords);
        return map;
    }
}
