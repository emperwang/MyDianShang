package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_keywords").is(keywords.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(COLLECTION, query, TbItem.class);
        resultes.put("rows",page.getContent());
        return resultes;
    }
}
