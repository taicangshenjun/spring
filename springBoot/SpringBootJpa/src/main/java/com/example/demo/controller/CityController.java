package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.CityRepository;
import com.example.demo.domain.City;

@Controller("cityController")
@RequestMapping("/cityController")
public class CityController {
	
	@Autowired
	@Qualifier("cityRepository")
	private CityRepository cityRepository;
	
	@RequestMapping("/findAll")
	public String findAll(Model model, HttpServletRequest request){
		List<City> resultList = cityRepository.findAll();
		model.addAttribute("resultList", resultList);
		return "city";
	}

}
