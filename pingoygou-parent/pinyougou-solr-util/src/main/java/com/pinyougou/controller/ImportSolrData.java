package com.pinyougou.controller;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/solr")
public class ImportSolrData {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    private final String COLLECTION="collection1";

    @GetMapping("import.do")
    public String importData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        // 设置动态域
        /*
        "id": "691300",
        "item_title": "三星 B9120 钛灰色 联通3G手机 双卡双待双通",
        "item_price": 4399,
        "item_image": "http://img10.360buyimg.com/n1/s450x450_jfs/t3457/294/236823024/102048/c97f5825/58072422Ndd7e66c4.jpg",
        "item_goodsid": 1,
        "item_category": "手机",
        "item_brand": "三星",
        "item_seller": "三星",
        "item_spec_网络": "联通3G",   // 就是操作此动态字段
        "item_spec_机身内存": "16G",
        "_version_": 1635570240376012800
         */
        for (TbItem item : tbItems) {
            String spec = item.getSpec();
            Map map = JSON.parseObject(spec, Map.class);
            item.setMapSpec(map);
        }
        solrTemplate.saveBeans(COLLECTION,tbItems);
        solrTemplate.commit(COLLECTION);
        return "import success";
    }
}
