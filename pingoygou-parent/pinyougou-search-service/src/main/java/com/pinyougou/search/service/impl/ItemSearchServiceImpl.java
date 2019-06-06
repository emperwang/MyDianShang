package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        // 1.高亮查询
        Map map = highLightQuery(keywords);
        resultes.putAll(map);

        // 2.分组查询商品分类列表
        List<String> strings = searchCategoryList(keywords);
        map.put("categoryList",strings);
        return resultes;
    }
    // 分类查询
    private List<String> searchCategoryList(Map keywords){
        List<String> list = new ArrayList<>();
        Query query = new SimpleQuery("*:*");
        // 设置查询关键字
        Criteria criteria = new Criteria("item_keywords").is(keywords.get("keywords"));
        query.addCriteria(criteria);
        // 添加分组的field
        GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        // 获取分组页
        GroupPage<TbItem> groupPage = solrTemplate.queryForGroupPage(COLLECTION, query, TbItem.class);
        // 获取分组结果对象
        GroupResult<TbItem> groupResult = groupPage.getGroupResult("item_category");
        // 获取分组入口集合
        Page<GroupEntry<TbItem>> entries = groupResult.getGroupEntries();
        for (GroupEntry<TbItem> entry : entries) {
            list.add(entry.getGroupValue());   // 将分组结果添加到列表中
        }
        return list;
    }

    // 高亮查询
    public Map highLightQuery(Map keywords){
        Map resultes = new HashMap();
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

    // 普通查询
    private List<TbItem> ordinaryQuery(Map keywords){
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_keywords").is(keywords.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(COLLECTION, query, TbItem.class);

        List<TbItem> content = page.getContent();
        return content;
    }
}
