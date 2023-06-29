package com.adin.media.controller;

import com.adin.join.vo.JoinVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.media.controller.MediaController")
public class MediaController {

    @GetMapping(value = "/media")
    public ModelAndView user(@RequestParam(value = "manage", required = false) String manage, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(manage == null) {
            modelAndView =  new ModelAndView("media_manage/introduce");
            if(joinVO != null) {
                modelAndView.addObject("nickname", joinVO.getNickname());
                modelAndView.addObject("email", joinVO.getEmail());
            }
        } else if("mediaRegister".equals(manage)) {

        }

        return  modelAndView;
    }
}
