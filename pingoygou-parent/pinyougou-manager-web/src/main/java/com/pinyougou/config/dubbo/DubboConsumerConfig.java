package com.pinyougou.config.dubbo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {"dubbo-consumer.xml"})
public class DubboConsumerConfig {
}
