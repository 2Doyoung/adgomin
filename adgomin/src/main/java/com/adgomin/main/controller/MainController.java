package com.adgomin.main.controller;

import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.adgomin.main.service.MainService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller(value = "com.adgomin.main.controller.MainController")
public class MainController {
	private static final String COOKIE = "adgominEncryptionCookie";
	private static final String SALT = "adgominEncryptionSalt";
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

		MediaRegisterVO[] mediaRegisterSns = this.mainService.popularSnsMedia();
		MediaRegisterVO[] mediaRegisterTransport = this.mainService.popularTransportMedia();
		MediaRegisterVO[] mediaRegisterOutdoor = this.mainService.popularOutdoorMedia();
		modelAndView.addObject("mediaRegisterSns", mediaRegisterSns);
		modelAndView.addObject("mediaRegisterTransport", mediaRegisterTransport);
		modelAndView.addObject("mediaRegisterOutdoor", mediaRegisterOutdoor);

        return modelAndView;
	}

	@GetMapping("/main/thumbnail/image")
	public ResponseEntity<Resource> mainThumbnailImage(@RequestParam(value = "thumbnailImgNm") String thumbnailImgNm, @RequestParam(value = "thumbnailImgFilePath") String thumbnailImgFilePath) {
		String filePath = null;

		filePath = thumbnailImgFilePath + "/" + thumbnailImgNm;


		Resource resource = new FileSystemResource(filePath);

		HttpHeaders header = new HttpHeaders();
		Path path = null;
		try {
			path = Paths.get(filePath);
			header.add("Content-type", Files.probeContentType(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
}
