package com.adgomin.portfolio.controller;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.service.PortfolioService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adgomin.portfolio.controller.PortfolioController")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping(value = "/portfolio")
    public ModelAndView portfolio(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView = new ModelAndView("portfolio/portfolio");
        } else {
            modelAndView = new ModelAndView("error/error");
        }

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

    @PostMapping("/portfolio/detail/img")
    @ResponseBody
    public String portfolioDetailImg(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile[] detailImgArr, PortfolioImgEntity portfolioImgEntity) {
        JSONObject responseObject = new JSONObject();
        portfolioImgEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.portfolioService.portfolioDetailImg(detailImgArr, portfolioImgEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
