package com.pinyougou.protal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.TbContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @RequestMapping("findByCategoryId.do")
    public List<TbContent> findByCategoryId(Long categoryId){
        List<TbContent> lists = contentService.findByCategoryId(categoryId);
        return lists;
    }
}
