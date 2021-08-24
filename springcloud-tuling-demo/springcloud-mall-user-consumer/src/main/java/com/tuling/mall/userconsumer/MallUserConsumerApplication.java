package com.tuling.mall.userconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

/**
 * @author Fox
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class MallUserConsumerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MallUserConsumerApplication.class,args);
    }
}
