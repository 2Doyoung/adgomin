package com.adin.introduce.controller;

import com.adin.introduce.service.IntroduceService;
import com.adin.media.vo.MediaIntroduceVO;
import com.adin.portfolio.entity.PortfolioEntity;
import com.adin.portfolio.vo.PortfolioVO;
import com.adin.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.introduce.controller.IntroduceController")
public class IntroduceController {
    private final IntroduceService introduceService;
    private final PostService postService;

    @Autowired
    public IntroduceController(IntroduceService introduceService, PostService postService) {
        this.introduceService = introduceService;
        this.postService = postService;
    }

    @GetMapping(value = "/introduce/{userOrder}")
    public ModelAndView introduce(@PathVariable("userOrder") int userOrder) {
        ModelAndView modelAndView = new ModelAndView("introduce/introduce");

        MediaIntroduceVO getIntroduce = this.introduceService.getIntroduce(userOrder);

        PortfolioVO getPortfolioCnt = this.postService.getPortfolioCnt(getIntroduce.getEmail());

        if(getPortfolioCnt.getCount() != 0) {
            PortfolioEntity[] getPortfolio = this.postService.getPortfolio(getIntroduce.getEmail());
            modelAndView.addObject("getPortfolio", getPortfolio);
        }

        modelAndView.addObject("email", getIntroduce.getEmail());
        modelAndView.addObject("mediaIntroduce", getIntroduce.getMediaIntroduce());
        modelAndView.addObject("region", getIntroduce.getRegion());
        modelAndView.addObject("adCategory", getIntroduce.getAdCategory());
        modelAndView.addObject("mediaUrl", getIntroduce.getMediaUrl());
        modelAndView.addObject("nickname", getIntroduce.getNickname());
        modelAndView.addObject("profileImgNm", getIntroduce.getProfileImgNm());
        modelAndView.addObject("profileOriginFileNm", getIntroduce.getProfileOriginFileNm());
        modelAndView.addObject("profileImgFilePath", getIntroduce.getProfileImgFilePath());

        return  modelAndView;
    }

    @GetMapping(value = "/introduce/all/portfolio/{userOrder}")
    public ModelAndView introduceAllPortfolio(@PathVariable("userOrder") int userOrder) {
        ModelAndView modelAndView = new ModelAndView("introduce/all_portfolio");

        MediaIntroduceVO getIntroduce = this.introduceService.getIntroduce(userOrder);

        PortfolioEntity[] getPortfolio = this.postService.getPortfolio(getIntroduce.getEmail());
        modelAndView.addObject("getPortfolio", getPortfolio);

        modelAndView.addObject("email", getIntroduce.getEmail());
        modelAndView.addObject("nickname", getIntroduce.getNickname());
        modelAndView.addObject("profileImgNm", getIntroduce.getProfileImgNm());
        modelAndView.addObject("profileOriginFileNm", getIntroduce.getProfileOriginFileNm());
        modelAndView.addObject("profileImgFilePath", getIntroduce.getProfileImgFilePath());

        return  modelAndView;
    }
}
