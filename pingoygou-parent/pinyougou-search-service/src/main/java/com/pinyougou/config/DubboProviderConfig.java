package com.pinyougou.config;

import com.alibaba.dubbo.config.*;
import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.search.service.impl.ItemSearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboProviderConfig {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("pinyougou-sellergoods-service");
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
    public ServiceConfig<ItemSearchService> brandServiceConfig(ItemSearchServiceImpl service){
        ServiceConfig<ItemSearchService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ItemSearchService.class);
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
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

    @Bean
    public MonitorConfig monitorConfig(){
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");
        return monitorConfig;
    }
}
