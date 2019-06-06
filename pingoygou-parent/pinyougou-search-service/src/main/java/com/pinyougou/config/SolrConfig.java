package com.pinyougou.config;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
public class SolrConfig {

    @Bean
    public SolrTemplate getSolrTemplate(SolrClient solrClient){
        SolrTemplate solrTemplate = new SolrTemplate(solrClient);
        return solrTemplate;
    }
}
