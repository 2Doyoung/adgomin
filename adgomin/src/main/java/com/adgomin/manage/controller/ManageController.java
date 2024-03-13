package com.adgomin.manage.controller;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.manage.service.ManageService;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
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
        modelAndView =  new ModelAndView("manage/media");

        MediaRegisterVO mediaOrderEmail = this.manageService.mediaOrderEmail(mediaOrder, joinVO.getEmail());

        if(joinVO != null && mediaOrderEmail.getCnt() != 0) {
            MediaRegisterEntity[] allMediaRegister = this.manageService.allMediaRegister(joinVO.getEmail());

            modelAndView.addObject("allMediaRegister", allMediaRegister);
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
}
