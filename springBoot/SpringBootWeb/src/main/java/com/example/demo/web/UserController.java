package com.example.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;

@RestController("UserController")
@RequestMapping("/user")
public class UserController {

	@Value("${book.name}")
	private String name;
	
	@Value("${book.author}")
	private String author;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping("/findOne")
	public User findOne() {
		logger.info("log:this function is findOne!");
		User user = new User();
		user.setUserName("cm");
		user.setPassWord("123456");
		return user;
	}
	
	@RequestMapping("/getBookInfo")
	public String getBookInfo() {
		return "bookName:" + name + ", author:" + author;
	}
	
}
