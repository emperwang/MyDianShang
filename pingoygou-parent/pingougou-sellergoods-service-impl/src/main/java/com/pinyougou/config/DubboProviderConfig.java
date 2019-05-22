package com.pinyougou.config;

import com.alibaba.dubbo.config.*;
import com.pinyougou.service.*;
import com.pinyougou.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

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
    public ServiceConfig<BrandService> brandServiceConfig(BrandServiceImpl service){
        ServiceConfig<BrandService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(BrandService.class);
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
    public ServiceConfig<SpecificationService> specificationServiceConfig(SpecificationServiceImpl specificationService){
        ServiceConfig<SpecificationService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(SpecificationService.class);
        serviceConfig.setRef(specificationService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ServiceConfig<SpecificationOptionService> specificationOptionServiceConfig(SpecificationOptionServiceImpl specificationService){
        ServiceConfig<SpecificationOptionService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(SpecificationOptionService.class);
        serviceConfig.setRef(specificationService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ServiceConfig<GoodsDescService> goodsDescServiceConfig(GoodsDescServiceImpl goodsDescService){
        ServiceConfig<GoodsDescService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(GoodsDescService.class);
        serviceConfig.setRef(goodsDescService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ServiceConfig<GoodsService> goodsServiceConfig(GoodsServiceImpl goodsService){
        ServiceConfig<GoodsService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(GoodsService.class);
        serviceConfig.setRef(goodsService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }
    @Bean
    public ServiceConfig<ItemCatService> itemCatServiceConfig(ItemCatServiceImpl itemCatService){
        ServiceConfig<ItemCatService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ItemCatService.class);
        serviceConfig.setRef(itemCatService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ServiceConfig<ItemService> itemServiceConfig(ItemServiceImpl itemService){
        ServiceConfig<ItemService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ItemService.class);
        serviceConfig.setRef(itemService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }

    @Bean
    public ServiceConfig<SellerService> sellerServiceConfig(SellerServiceImpl sellerService){
        ServiceConfig<SellerService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(SellerService.class);
        serviceConfig.setRef(sellerService);
        serviceConfig.setRetries(0);
        serviceConfig.setRegistry(registryConfig());
        serviceConfig.setApplication(applicationConfig());
        serviceConfig.setProtocol(protocolConfig());
        serviceConfig.setMonitor(monitorConfig());
        serviceConfig.export();
        return serviceConfig;
    }
    @Bean
    public ServiceConfig<TypeTemplateService> typeTemplateServiceConfig(TypeTemplateServiceImpl typeTemplateService){
        ServiceConfig<TypeTemplateService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(TypeTemplateService.class);
        serviceConfig.setRef(typeTemplateService);
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
