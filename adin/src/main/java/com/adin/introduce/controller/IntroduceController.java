package com.adin.introduce.controller;

import com.adin.common.CategoryCriteria;
import com.adin.common.CategoryPaging;
import com.adin.introduce.service.IntroduceService;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.introduce.controller.IntroduceController")
public class IntroduceController {
    private final IntroduceService introduceService;

    @Autowired
    public IntroduceController(IntroduceService introduceService) {
        this.introduceService = introduceService;
    }

    @GetMapping(value = "/introduce")
    public ModelAndView introduce() {
        ModelAndView modelAndView = new ModelAndView("introduce/introduce");

        return  modelAndView;
    }

}
