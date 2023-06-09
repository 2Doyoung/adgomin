package com.adin.user.controller;

import com.adin.join.entity.JoinEntity;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String profileImageChange(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile profileFile) {
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

    @PatchMapping("/change/nickname")
    @ResponseBody
    public String nicknameChange(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "nickname", required = false) String nickname) {
        JSONObject responseObject = new JSONObject();
        joinVO.setNickname(nickname);
        Enum<?> result = this.userService.changeNickname(joinVO);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PostMapping("/password/check")
    @ResponseBody
    public String passwordCheck(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "password", required = false) String password) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.userService.passwordCheck(joinVO, password);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/user/passwordChange")
    @ResponseBody
    public String userPasswordChange(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "password", required = false) String password) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.userService.userPasswordChange(joinVO, password);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @DeleteMapping("/user/secession")
    @ResponseBody
    public String userSecession(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "secessionReason", required = false) String secessionReason, HttpServletRequest request, HttpServletResponse response) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.userService.userSecession(joinVO);
        responseObject.put("result", result.name().toLowerCase());
        if("success".equals(result.name().toLowerCase())) {
            this.userService.userSecessionReason(joinVO, secessionReason);

            HttpSession session = request.getSession();
            session.invalidate();

            Cookie cookie = new Cookie("REMEMBER", null);
            Cookie cookie2 = new Cookie("JSESSIONID", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie2.setMaxAge(0);
            cookie2.setPath("/");
            response.addCookie(cookie2);
        }

        return responseObject.toString();
    }
}
