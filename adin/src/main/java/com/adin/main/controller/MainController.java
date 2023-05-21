package com.adin.main.controller;

import com.adin.join.vo.JoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.adin.main.service.MainService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	private static final String COOKIE = "adinEncryptionCookie";
	private static final String SALT = "adinEncryptionSalt";
	private final MainService mainService;
	
	@Autowired
	public MainController(MainService mainService) {
        this.mainService = mainService;
    }
	
	/**
	 * GET : 메인페이지
	 */
	@GetMapping(value = "/")
	public ModelAndView main(@CookieValue(value = "REMEMBER", required = false) String remember, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("main/main");

		if(remember != null) {
			byte[] cookieBytes = COOKIE.getBytes();
			byte[] saltBytes = SALT.getBytes();

			TextEncryptor encryptor = Encryptors.text(new String(Hex.encode(cookieBytes)), new String(Hex.encode(saltBytes)));
			String decryptedEmail = encryptor.decrypt(remember);

			this.mainService.setSession(decryptedEmail, request);
		}

        return modelAndView;
	}
}
