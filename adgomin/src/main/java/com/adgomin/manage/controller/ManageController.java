package com.adgomin.manage.controller;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.manage.service.ManageService;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adgomin.manage.controller.ManageController")
public class ManageController {
    private final ManageService manageService;

    @Autowired
    public ManageController(ManageService manageService) {
        this.manageService = manageService;
    }

    @GetMapping(value = "/manage")
    public ModelAndView manage(@RequestParam(value = "manage", required = false) String manage, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if("media".equals(manage)) {
            modelAndView =  new ModelAndView("manage/media");
            if(joinVO != null) {
                MediaRegisterEntity[] allMediaRegister = this.manageService.allMediaRegister(joinVO.getEmail());
            }
        }

        return  modelAndView;
    }
}
