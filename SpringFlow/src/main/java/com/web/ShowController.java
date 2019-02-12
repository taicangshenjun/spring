package com.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ShowController")
@RequestMapping("/show")
public class ShowController {
	
	@RequestMapping("/hello")
	public String helloWorld() {
		return "hello";
	}
	
}
