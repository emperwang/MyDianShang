package com.pinyougou.protal.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.content.service.ContentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo
public class DubboConfig {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("pinyougou-portal-web");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1:2181");
        return registryConfig;
    }
    @Bean
    public ReferenceConfig referenceConfig(){
        ReferenceConfig<ContentService> contentServiceReferenceConfig = new ReferenceConfig<>();
        contentServiceReferenceConfig.setInterface(ContentService.class);
        return contentServiceReferenceConfig;
    }

    @Bean
    public ReferenceConfig referenceConfig2(){
        ReferenceConfig<ContentCategoryService> contentCategoryServiceReferenceConfig = new ReferenceConfig<>();
        contentCategoryServiceReferenceConfig.setInterface(ContentCategoryService.class);
        return contentCategoryServiceReferenceConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(5000);
        return consumerConfig;
    }

    @Bean
    public MonitorConfig monitorConfig(){
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }
}
