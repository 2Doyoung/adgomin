package com.adin.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.adin.main.service.MainService;

@Controller
public class MainController {
	
	private final MainService mainService;
	
	@Autowired
	public MainController(MainService mainService) {
        this.mainService = mainService;
    }
	
	/**
	 * GET : 메인페이지
	 */
	@GetMapping(value = "/")
	public ModelAndView main() {
		ModelAndView modelAndView = new ModelAndView("main/main");
        
        return modelAndView;
	}
}
