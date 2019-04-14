package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demoController")
@RestController("demoController")
public class DemoController {
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping("/demo")
	public String demo() {
		return "hi, i am from: " + port;
	}

}
