package com.adin.join.controller;

import com.adin.join.entity.JoinEntity;
import com.adin.join.service.JoinService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller(value = "com.adin.join.controller.JoinController")
public class JoinController {
    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping(value = "/join")
    public ModelAndView getJoin(@RequestParam(value = "user_type", required = false) String user_type) {
        ModelAndView modelAndView = null;
        if(user_type == null) {
            modelAndView = new ModelAndView("join/join");
        } else if(("media").equals(user_type) || ("advertiser").equals(user_type)) {
            modelAndView = new ModelAndView("join/next_step");
            modelAndView.addObject("user_type", user_type);
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @PostMapping("/join")
    @ResponseBody
    public String postJoin(JoinEntity joinEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.joinService.join(joinEntity);
        responseObject.put("result", result.name().toLowerCase());
        responseObject.put("email", joinEntity.getEmail());
        responseObject.put("certified", joinEntity.getCertified());

        return responseObject.toString();
    }

    @GetMapping("/join/emailDuplicate")
    @ResponseBody
    public String emailDuplicate(@RequestParam(value = "email", required = false) String email) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.joinService.emailCheck(email);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PostMapping("/login")
    @ResponseBody
    public String postLogin(JoinEntity joinEntity, HttpServletRequest request, @RequestParam(value = "rememberMe", required = false) boolean rememberMe, HttpServletResponse response) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.joinService.login(joinEntity, request, rememberMe, response);
        responseObject.put("result", result.name().toLowerCase());
        if("no_certified_user".equals(result.name().toLowerCase())) {
            responseObject.put("email", joinEntity.getEmail());
        }

        return responseObject.toString();
    }

    @GetMapping("/join/email/certified")
    public String emailCertified(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "certified", required = false) String certified, HttpServletRequest request) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.joinService.certifiedCheck(email, certified, request);
        responseObject.put("result", result.name().toLowerCase());

        return "redirect:http://localhost:8080";
    }

    @GetMapping(value = "/join/email")
    public ModelAndView getJoinEmail() {
        ModelAndView modelAndView = new ModelAndView("join/join_email");

        return  modelAndView;
    }

    @GetMapping(value = "/join/noCertified")
    @ResponseBody
    public ModelAndView getJoinNoCertified(@RequestParam(value = "email", required = false) String email) {
        ModelAndView modelAndView = new ModelAndView("join/join_no_certified");
        modelAndView.addObject("email", email);
        return  modelAndView;
    }
}
