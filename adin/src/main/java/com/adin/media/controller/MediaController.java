package com.adin.media.controller;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.service.MediaService;
import com.adin.user.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.media.controller.MediaController")
public class MediaController {

    private final MediaService mediaService;

    private final UserService userService;

    public MediaController(MediaService mediaService, UserService userService) {
        this.mediaService = mediaService;
        this.userService = userService;
    }

    @GetMapping(value = "/media")
    public ModelAndView media(@RequestParam(value = "manage", required = false) String manage, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(manage == null) {
            modelAndView =  new ModelAndView("media_manage/introduce");
            if(joinVO != null) {
                modelAndView.addObject("nickname", joinVO.getNickname());
                modelAndView.addObject("email", joinVO.getEmail());

                MediaIntroduceEntity mediaIntroduceEntity = this.mediaService.getMediaIntroduce(joinVO.getEmail());

                if(mediaIntroduceEntity != null) {
                    modelAndView.addObject("mediaIntroduce", mediaIntroduceEntity.getMediaIntroduce());
                    modelAndView.addObject("region", mediaIntroduceEntity.getRegion());
                    modelAndView.addObject("adCategory", mediaIntroduceEntity.getAdCategory());
                    modelAndView.addObject("mediaUrl", mediaIntroduceEntity.getMediaUrl());
                }
            }
        } else if("mediaRegister".equals(manage)) {
            modelAndView =  new ModelAndView("media_manage/register");
        }

        return  modelAndView;
    }

    @PatchMapping("/media/introduce")
    @ResponseBody
    public String mediaIntroduce(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, JoinEntity joinEntity, MediaIntroduceEntity mediaIntroduceEntity) {
        JSONObject responseObject = new JSONObject();

        joinVO.setNickname(joinEntity.getNickname());
        mediaIntroduceEntity.setEmail(joinVO.getEmail());

        Enum<?> result = this.userService.changeNickname(joinVO);
        if("success".equals(result.name().toLowerCase())) {
            result = this.mediaService.mediaIntroduce(mediaIntroduceEntity);
        }
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
