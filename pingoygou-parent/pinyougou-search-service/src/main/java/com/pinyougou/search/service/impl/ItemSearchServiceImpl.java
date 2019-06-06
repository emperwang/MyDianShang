package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    private final String COLLECTION="collection1";

    @Override
    public Map itemSearch(Map keywords) {
        Map resultes = new HashMap();
        // 第一种查询方式 没有高亮显示
        /*Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_keywords").is(keywords.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(COLLECTION, query, TbItem.class);
        resultes.put("rows",page.getContent());*/

        // 第二种查询方式，高亮显示
        HighlightQuery query = new SimpleHighlightQuery();
        // 设置高亮字段
        HighlightOptions highLightOption = new HighlightOptions();
        // 具体设置字段，  可以设置多个字段
        highLightOption.addField("item_title");
        highLightOption.setSimplePrefix("<em style='color:red'>");
        highLightOption.setSimplePostfix("</em>");

        query.setHighlightOptions(highLightOption);

        Criteria criteria = new Criteria("item_keywords").is(keywords.get("keywords"));
        query.addCriteria(criteria);
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(COLLECTION, query, TbItem.class);
        // 高亮字段入口
        List<HighlightEntry<TbItem>> entries = page.getHighlighted();
        // 把具体的高亮字段 更新到类中
        for (HighlightEntry<TbItem> entry : entries) {
            String s = entry.getHighlights().get(0).getSnipplets().get(0);
            TbItem entity = entry.getEntity();
            entity.setTitle(s);
        }
        resultes.put("rows",page.getContent());
        return resultes;
    }
}
