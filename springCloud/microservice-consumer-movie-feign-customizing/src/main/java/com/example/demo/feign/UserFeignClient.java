package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.User;

import feign.Param;
import feign.RequestLine;

/**
 * Feign的默认配置类是FeignClientsConfiguration，该类定义了Feign默认使用的编码器，解码器，使用契约等
 * @FeignClient的configuration属性可以自定义Feign配置，优先级高于FeignClientsConfiguration
 */
@FeignClient(name = "microservice-provider-user", configuration = FeignConfiguration.class)
@Configuration
public interface UserFeignClient {

	/**
	 * 使用feign自带的注解@RequestLine
	 * @see https://github.com/OpenFeign/feign
	 * @param id
	 * @return
	 */
	@RequestLine(value = "GET/userController/findById/{id}")
	public User findById(@Param("id") String id);
	
}
