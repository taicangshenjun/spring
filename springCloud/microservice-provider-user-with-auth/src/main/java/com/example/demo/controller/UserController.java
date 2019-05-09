package com.example.demo.controller;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

@RestController("userController")
@RequestMapping("/userController")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	/**
	 * request头,application/json;charset=UTF-8返回json
	 */
	@GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findById(@PathVariable("id") String id) {
		Object printcipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(printcipal instanceof UserDetails) {
			UserDetails user = (UserDetails) printcipal;
			Collection<? extends GrantedAuthority> collection = user.getAuthorities();
			for(GrantedAuthority g: collection) {
				logger.info("当前用户是{}，角色是{}", user.getUsername(), g.getAuthority());
			}
		}else {
			logger.info("失败");
		}
		//Optional类中有个get()方法，返回的是当前对象/值
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.isPresent()? userOpt.get(): null;
	}
	
}
