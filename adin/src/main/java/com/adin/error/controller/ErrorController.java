package com.adin.error.controller;

import com.adin.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
	@GetMapping(value = "/errorpage")
	public ModelAndView error() {
		ModelAndView modelAndView = new ModelAndView("error/error");

        return modelAndView;
	}
}
