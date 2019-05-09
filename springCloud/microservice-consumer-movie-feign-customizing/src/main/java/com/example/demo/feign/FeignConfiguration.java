package com.example.demo.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;

@Configuration
public class FeignConfiguration {

	@Bean
	public Contract feignConfig() {
		return new feign.Contract.Default();
	}
	
//	@Bean
//	public BasicAuthenticationInterceptor basicAuthenticationInterceptor() {
//		return new BasicAuthenticationInterceptor("user", "password");
//	}
//	
}
