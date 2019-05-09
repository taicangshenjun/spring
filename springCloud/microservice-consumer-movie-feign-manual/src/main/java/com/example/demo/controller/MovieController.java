package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.feign.UserFeignClient;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

/**
 * 为服务消费者整合feign
 * @Import 导入的FeignClientConfiguration是Spring Cloud为Feign默认提供的配置类
 * @author cm
 *
 */
@Import(FeignClientsConfiguration.class)
@RestController("movieController")
@RequestMapping("/movieController")
public class MovieController {
	
	private UserFeignClient userFeignClient;
	
	private UserFeignClient adminFeignClient;
	
	@Autowired
	public MovieController(Decoder decoder, Encoder encoder, Client client, Contract contract) {
		this.userFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
				.requestInterceptor(new BasicAuthRequestInterceptor("user", "password1"))
				.target(UserFeignClient.class, "http://microservice-provider-user/");
		this.adminFeignClient = Feign.builder().client(client).encoder(encoder).decoder(decoder).contract(contract)
				.requestInterceptor(new BasicAuthRequestInterceptor("admin", "password2"))
				.target(UserFeignClient.class, "http://microservice-provider-user/");
	}

	@GetMapping(value = "/user-user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findByIdUser(@PathVariable("id") String id) {
		return this.userFeignClient.findById(id);
	}
	
	@GetMapping(value = "/user-admin/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findByIdAdmin(@PathVariable("id") String id) {
		return this.adminFeignClient.findById(id);
	}
	
}
