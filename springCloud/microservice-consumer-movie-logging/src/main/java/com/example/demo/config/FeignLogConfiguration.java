package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.Logger.Level;

@Configuration
public class FeignLogConfiguration {

	@Bean
	public Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
	
}
