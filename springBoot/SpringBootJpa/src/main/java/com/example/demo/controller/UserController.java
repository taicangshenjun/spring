package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.domain.User;

@RestController
@RequestMapping("/userController")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/findAll")
	public List<User> findAll(Model model, HttpServletRequest request){
		List<User> resultList = userRepository.findAll();
		return resultList;
	}
	
	@RequestMapping("/findByName")
	public User findByName(Model model, HttpServletRequest request) {
		String name = request.getParameter("name");
		User user = null;
		if(name != null && !"".equals("name")) {
			user = userRepository.findByName(name);
		}
		return user;
	}
	
	@RequestMapping("/save")
	public List<User> save() {
		User user1 = new User();
		user1.setId(UUID.randomUUID().toString());
		user1.setName("张三");
		user1.setAge(26);
		user1.setSex("男");
		user1.setSalary(15000D);
		
		User user2 = new User();
		user2.setId(UUID.randomUUID().toString());
		user2.setName("李四");
		user2.setAge(25);
		user2.setSex("女");
		user2.setSalary(8000D);
		
		User user3 = new User();
		user3.setId(UUID.randomUUID().toString());
		user3.setName("王五");
		user3.setAge(25);
		user3.setSex("男");
		user3.setSalary(12000D);
		
		User user4 = new User();
		user4.setId(UUID.randomUUID().toString());
		user4.setName("赵六");
		user4.setAge(23);
		user4.setSex("女");
		user4.setSalary(10000D);
		
		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		userList.add(user4);
		userRepository.saveAll(userList);
		return userList;
	}
	
	@RequestMapping("/deleteAll")
	public void deleteAll() {
		userRepository.deleteAll();
	}
	
}
