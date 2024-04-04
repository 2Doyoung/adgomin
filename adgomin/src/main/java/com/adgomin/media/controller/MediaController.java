package com.adgomin.media.controller;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.service.MediaService;
import com.adgomin.user.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller(value = "com.adgomin.media.controller.MediaController")
public class MediaController {

    private final MediaService mediaService;

    private final UserService userService;
    @Autowired
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
            } else {
                modelAndView = new ModelAndView("error/error");
            }
        } else if("mediaRegister".equals(manage)) {
            modelAndView =  new ModelAndView("media_manage/register");
            if(joinVO != null) {
                MediaRegisterEntity mediaRegisterEntity = this.mediaService.getMediaRegister(joinVO.getEmail());
                modelAndView.addObject("mediaOrder", mediaRegisterEntity.getMediaOrder());
                modelAndView.addObject("adDetailCategory", mediaRegisterEntity.getAdDetailCategory());
                modelAndView.addObject("mediaTitle", mediaRegisterEntity.getMediaTitle());
                modelAndView.addObject("mediaSummary", mediaRegisterEntity.getMediaSummary());
                modelAndView.addObject("mediaDetailExplain", mediaRegisterEntity.getMediaDetailExplain());
                modelAndView.addObject("thumbnailImgNm", mediaRegisterEntity.getThumbnailImgNm());
                modelAndView.addObject("thumbnailOriginFileNm", mediaRegisterEntity.getThumbnailOriginFileNm());
                modelAndView.addObject("thumbnailImgFilePath", mediaRegisterEntity.getThumbnailImgFilePath());
                modelAndView.addObject("mediaPrice", mediaRegisterEntity.getMediaPrice());
                modelAndView.addObject("mediaSubmitStatus", mediaRegisterEntity.getMediaSubmitStatus());
            } else {
                modelAndView = new ModelAndView("error/error");
            }
        } else {
            modelAndView = new ModelAndView("error/error");
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

    @PatchMapping("/media/register")
    @ResponseBody
    public String mediaRegister(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.mediaService.mediaRegister(mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/change/thumbnail")
    @ResponseBody
    public String changeThumbnail(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile thumbnail, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.mediaService.changeThumbnail(thumbnail, mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @GetMapping("/thumbnail/image")
    public ResponseEntity<Resource> thumbnailImage(@RequestParam(value = "email") String email) {
        MediaRegisterEntity mediaRegisterEntity = this.mediaService.thumbnailImage(email);
        String filePath = null;

        if(mediaRegisterEntity != null) {
            filePath = mediaRegisterEntity.getThumbnailImgFilePath() + "/" + mediaRegisterEntity.getThumbnailImgNm();
        } else if(mediaRegisterEntity == null) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("static/images/media-register-thumbnail.png").getFile());
            filePath = file.getAbsolutePath();
        }

        Resource resource = new FileSystemResource(filePath);

        HttpHeaders header = new HttpHeaders();
        Path path = null;
        try {
            path = Paths.get(filePath);
            header.add("Content-type", Files.probeContentType(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    @PostMapping("/media/detail/explanation/image")
    @ResponseBody
    public String mediaDetailExplanationImage(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile mediaDetailExplanationImage) {
        JSONObject responseObject = new JSONObject();
        String path = this.mediaService.mediaDetailExplanationImage(joinVO.getEmail(), mediaDetailExplanationImage);
        responseObject.put("result", path);

        return responseObject.toString();
    }

    @GetMapping("/media/detail/image/display")
    public ResponseEntity<Resource> mediaDetailImageDisplay(@RequestParam(value = "uploadPath") String uploadPath) {
        String filePath = uploadPath;


        Resource resource = new FileSystemResource(filePath);

        HttpHeaders header = new HttpHeaders();
        Path path = null;
        try {
            path = Paths.get(filePath);
            header.add("Content-type", Files.probeContentType(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
}
