package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class RibbonConfig {

	@Bean
	public IRule ribbonRule() {
		//负载均衡规则，改为随机
		return new RandomRule();
	}
	
}
