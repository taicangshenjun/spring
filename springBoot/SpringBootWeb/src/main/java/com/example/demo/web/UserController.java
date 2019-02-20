package com.example.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;

@RestController("UserController")
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/findOne")
	public User find() {
		User user = new User();
		user.setUserName("aly");
		user.setPassWord("123456");
		return user;
	}
	
}
