package com.adin.user.controller;

import com.adin.join.vo.JoinVO;
import com.adin.user.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller(value = "com.adin.user.controller.UserController")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping(value = "/user")
    public ModelAndView user(@RequestParam(value = "setting", required = false) String setting, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(setting == null) {
            modelAndView = new ModelAndView("user/user");
            if(joinVO != null) {
                modelAndView.addObject("nickname", joinVO.getNickname());
                modelAndView.addObject("email", joinVO.getEmail());
            }
        } else if("userPasswordChange".equals(setting)) {
            modelAndView = new ModelAndView("user/password_change");
        } else if("userSecession".equals(setting)) {
            modelAndView = new ModelAndView("user/user_secession");
        }

        return  modelAndView;
    }

    @PatchMapping("/change/profileImg")
    @ResponseBody
    public String passwordChange(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile profileFile) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.userService.changeProfileImg(joinVO, profileFile);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @GetMapping("/profile/image")
    public ResponseEntity<Resource> profileImage(@RequestParam(value = "email") String email) {
        JoinVO joinVO = this.userService.profileImage(email);
        String filePath = null;

        if(joinVO != null) {
            filePath = joinVO.getProfileImgFilePath() + "/" + joinVO.getProfileImgNm();
        } else if(joinVO == null) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("static/images/profile.png").getFile());
            filePath = file.getAbsolutePath();
        }

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
