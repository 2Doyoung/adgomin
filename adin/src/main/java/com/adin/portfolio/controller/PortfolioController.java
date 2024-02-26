package com.adin.portfolio.controller;

import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.vo.MediaIntroduceVO;
import com.adin.portfolio.entity.PortfolioEntity;
import com.adin.portfolio.service.PortfolioService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        ModelAndView modelAndView = new ModelAndView("portfolio/portfolio");;

        return  modelAndView;
    }

    @PostMapping("/portfolio/register")
    @ResponseBody
    public String portfolioRegister(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, PortfolioEntity portfolioEntity) {
        JSONObject responseObject = new JSONObject();
        portfolioEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.portfolioService.portfolioRegister(portfolioEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/portfolio/change/thumbnail")
    @ResponseBody
    public String portfolioChangeThumbnail(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile thumbnail, PortfolioEntity portfolioEntity) {
        JSONObject responseObject = new JSONObject();
        portfolioEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.portfolioService.portfolioChangeThumbnail(thumbnail, portfolioEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
