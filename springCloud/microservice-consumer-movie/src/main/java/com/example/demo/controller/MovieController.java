package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.User;

@RestController("movieController")
@RequestMapping("/movieController")
public class MovieController {

	@Autowired
	@Qualifier("oneTamplate")
	private RestTemplate restTemplate;
	
	@GetMapping("/user/{id}")
	public User findById(@PathVariable("id") String id) {
		String url = "http://localhost:9080/springboot/userController/findById/" + id;
		return this.restTemplate.getForObject(url, User.class);
	}
	
}
