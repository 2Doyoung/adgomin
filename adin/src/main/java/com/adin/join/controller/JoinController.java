package com.adin.join.controller;

import com.adin.join.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.join.controller.JoinController")
@RequestMapping(value = "/join")
public class JoinController {
    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping(value = "")
    public ModelAndView getJoin() {
        ModelAndView modelAndView = new ModelAndView("join/join");
        return  modelAndView;
    }
}
