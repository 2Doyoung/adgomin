package com.adin.portfolio.controller;

import com.adin.join.vo.JoinVO;
import com.adin.media.vo.MediaIntroduceVO;
import com.adin.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.portfolio.controller.PortfolioController")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping(value = "/portfolio")
    public ModelAndView portfolio(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = new ModelAndView("portfolio/portfolio");

        return  modelAndView;
    }
}
