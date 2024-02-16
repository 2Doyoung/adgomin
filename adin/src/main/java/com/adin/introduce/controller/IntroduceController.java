package com.adin.introduce.controller;

import com.adin.common.CategoryCriteria;
import com.adin.common.CategoryPaging;
import com.adin.introduce.service.IntroduceService;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.vo.MediaIntroduceVO;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.introduce.controller.IntroduceController")
public class IntroduceController {
    private final IntroduceService introduceService;

    @Autowired
    public IntroduceController(IntroduceService introduceService) {
        this.introduceService = introduceService;
    }

    @GetMapping(value = "/introduce/{userOrder}")
    public ModelAndView introduce(@PathVariable("userOrder") int userOrder) {
        ModelAndView modelAndView = new ModelAndView("introduce/introduce");

        MediaIntroduceVO getIntroduce = this.introduceService.getIntroduce(userOrder);

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

}
