package com.pinyougou.config;

import com.alibaba.dubbo.config.*;
import com.pinyougou.content.service.ContentCategoryService;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.service.impl.ContentCategoryServiceImpl;
import com.pinyougou.service.impl.ContentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboProviderConfig {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("pinyougou-content-service");
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
    public ServiceConfig<ContentCategoryService> contentCategoryServiceConfig(ContentCategoryServiceImpl service){
        ServiceConfig<ContentCategoryService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ContentCategoryService.class);
        serviceConfig.setRef(service);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ServiceConfig<ContentService> contentServiceConfig(ContentServiceImpl service){
        ServiceConfig<ContentService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ContentService.class);
        serviceConfig.setRef(service);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20881);
        return protocolConfig;
    }

    @Bean
    public MonitorConfig monitorConfig(){
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }
}
