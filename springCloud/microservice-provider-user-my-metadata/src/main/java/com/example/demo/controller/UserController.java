package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@RestController("userController")
@RequestMapping("/userController")
public class UserController {

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	/**
	 * request头,application/json;charset=UTF-8返回json
	 */
	@GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findById(@PathVariable("id") String id) {
		//Optional类中有个get()方法，返回的是当前对象/值
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.isPresent()? userOpt.get(): null;
	}
	
}
