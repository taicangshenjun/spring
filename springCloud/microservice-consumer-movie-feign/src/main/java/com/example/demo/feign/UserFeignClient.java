package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.User;

/**
 * Feign的默认配置类是FeignClientsConfiguration，该类定义了Feign默认使用的编码器，解码器，使用契约等
 * @FeignClient的configuration属性可以自定义Feign配置，优先级高于FeignClientsConfiguration
 */
@FeignClient(name = "microservice-provider-user")
@Configuration
public interface UserFeignClient {

	@RequestMapping(value = "/userController/findById/{id}", method = RequestMethod.GET)
	public User findById(@PathVariable("id") String id);
	
}
