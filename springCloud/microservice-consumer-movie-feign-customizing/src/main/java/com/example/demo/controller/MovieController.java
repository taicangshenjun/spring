package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.feign.UserFeignClient;

/**
 * 为服务消费者整合feign
 */
@RestController("movieController")
@RequestMapping("/movieController")
public class MovieController {

	@Autowired
	private UserFeignClient userFeignClient;
	
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findById(@PathVariable("id") String id) {
		return this.userFeignClient.findById(id);
	}
	
}
