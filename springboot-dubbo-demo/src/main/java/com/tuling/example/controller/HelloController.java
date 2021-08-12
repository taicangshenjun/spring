package com.tuling.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.example.service.HelloSerice;

@RestController
@RequestMapping("/helloController")
public class HelloController {
	
	@Autowired
	private HelloSerice helloService;
	
	@RequestMapping("/sayHello/{name}")
	public String sayHello(@PathVariable("name") String name) {
		return helloService.sayHello(name);
	}

}
