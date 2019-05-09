package com.example.demo.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * 使用@RibbonClient，为特定name的Ribbon Client自定义配置
 * @RibbonClient的configuration属性，指定Ribbon的配置类
 */
@Configuration
@RibbonClient(name = "microservice-provider-user", configuration = RibbonConfig.class)
public class TestConfiguration {

}
