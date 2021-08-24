package com.tuling.mall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
//@EnableDiscoveryClient
public class MallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }

}
