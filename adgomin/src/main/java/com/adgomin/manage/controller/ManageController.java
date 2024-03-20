package com.adgomin.manage.controller;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.manage.service.ManageService;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
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

                modelAndView.addObject("allMediaRegister", allMediaRegister);
            }
        }

        return  modelAndView;
    }

    @GetMapping(value = "/manage/media/update")
    public ModelAndView manageUpdate(@RequestParam(value = "mediaOrder", required = false) String mediaOrder, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        modelAndView =  new ModelAndView("manage/media_update");

        MediaRegisterVO mediaOrderEmail = this.manageService.mediaOrderEmail(mediaOrder, joinVO.getEmail());

        if(joinVO != null && mediaOrderEmail.getCnt() != 0) {
            MediaRegisterEntity mediaRegisterEntity = this.manageService.mediaRegisterEntity(mediaOrder);

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
            modelAndView =  new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @GetMapping("/manage/thumbnail/image")
    public ResponseEntity<Resource> mainThumbnailImage(@RequestParam(value = "thumbnailImgNm") String thumbnailImgNm, @RequestParam(value = "thumbnailImgFilePath") String thumbnailImgFilePath) {
        String filePath = null;

        if("".equals(thumbnailImgNm) || "".equals(thumbnailImgFilePath)) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("static/images/media-register-thumbnail.png").getFile());
            filePath = file.getAbsolutePath();
        } else {
            filePath = thumbnailImgFilePath + "/" + thumbnailImgNm;
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

    @GetMapping("/media/update/thumbnail/image")
    public ResponseEntity<Resource> mediaUpdateThumbnailImage(@RequestParam(value = "mediaOrder") String mediaOrder) {
        MediaRegisterEntity mediaRegisterEntity = this.manageService.mediaRegisterEntity(mediaOrder);
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

    @PatchMapping("/manage/media/update")
    @ResponseBody
    public String manageMediaUpdate(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.manageMediaUpdate(mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/manage/media/change/thumbnail")
    @ResponseBody
    public String manageMediaChangeThumbnail(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile thumbnail, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.manageMediaChangeThumbnail(thumbnail, mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @DeleteMapping("/manage/media/delete/add")
    @ResponseBody
    public String manageMediaDeleteAdd(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile thumbnail, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.manageMediaDeleteAdd(mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
