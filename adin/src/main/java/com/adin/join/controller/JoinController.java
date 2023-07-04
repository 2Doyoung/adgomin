package com.adin.join.controller;

import com.adin.join.entity.JoinEntity;
import com.adin.join.service.JoinService;
import com.adin.media.service.MediaService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller(value = "com.adin.join.controller.JoinController")
public class JoinController {
    private final JoinService joinService;
    private final MediaService mediaService;

    @Autowired
    public JoinController(JoinService joinService, MediaService mediaService) {
        this.joinService = joinService;
        this.mediaService = mediaService;
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
        if("success".equals(result.name().toLowerCase())) {
            responseObject.put("email", joinEntity.getEmail());
            responseObject.put("certified", joinEntity.getCertified());

            result = this.mediaService.insertMediaIntroduce(joinEntity);
        }
        responseObject.put("result", result.name().toLowerCase());

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
        if("failure".equals(result.name().toLowerCase())) {
            return "redirect:http://localhost:8080/errorpage";
        }
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

    @GetMapping("/logouts")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
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

        return "redirect:http://localhost:8080";
    }

    @GetMapping(value = "/passwordEmail")
    public ModelAndView passwordEmail() {
        ModelAndView modelAndView = new ModelAndView("join/password_email");

        return  modelAndView;
    }

    @GetMapping(value = "/passwordEmailAfter")
    public ModelAndView passwordEmailAfter() {
        ModelAndView modelAndView = new ModelAndView("join/password_email_after");

        return  modelAndView;
    }

    @GetMapping(value = "/password/change")
    public ModelAndView passwordChange(@RequestParam(value = "email", required = false) String email,  @RequestParam(value = "certified", required = false) String certified) {
        Enum<?> result = this.joinService.passwordCertifiedCheck(email, certified);
        ModelAndView modelAndView = null;
        if("success".equals(result.name().toLowerCase())) {
            modelAndView = new ModelAndView("join/password_change");
            modelAndView.addObject("email", email);
        } else if("failure".equals(result.name().toLowerCase())) {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @PatchMapping("/passwordChange")
    @ResponseBody
    public String passwordChange(JoinEntity joinEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.joinService.passwordChange(joinEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @GetMapping(value = "/password/change/complete")
    public ModelAndView passwordChangeComplete() {
        ModelAndView modelAndView = new ModelAndView("join/password_change_complete");

        return  modelAndView;
    }
}
