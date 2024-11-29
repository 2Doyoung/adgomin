package com.adgomin.manage.controller;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.manage.service.ManageService;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } else if("portfolio".equals(manage)) {
            modelAndView =  new ModelAndView("manage/portfolio");
            if(joinVO != null) {
                PortfolioEntity[] allPortfolio = this.manageService.allPortfolio(joinVO.getEmail());

                modelAndView.addObject("allPortfolio", allPortfolio);
                modelAndView.addObject("userOrder", joinVO.getUserOrder());
            }
        }

        return  modelAndView;
    }

    @GetMapping(value = "/manage/media/update")
    public ModelAndView manageMediaUpdate(@RequestParam(value = "mediaOrder", required = false) String mediaOrder, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView("manage/media_update");

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
            modelAndView.addObject("offerPeriod", mediaRegisterEntity.getOfferPeriod());
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
    public String manageMediaDeleteAdd(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.manageMediaDeleteAdd(mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @DeleteMapping("/manage/media/delete")
    @ResponseBody
    public String manageMediaDelete(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        mediaRegisterEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.manageMediaDelete(mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @GetMapping(value = "/manage/portfolio/update")
    public ModelAndView managePortfolioUpdate(@RequestParam(value = "portfolioOrder", required = false) String portfolioOrder, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView("manage/portfolio_update");

        PortfolioVO portfolioOrderEmail = this.manageService.portfolioOrderEmail(portfolioOrder, joinVO.getEmail());

        if(joinVO != null && portfolioOrderEmail.getCount() != 0) {
            PortfolioEntity portfolioEntity = this.manageService.portfolioEntity(portfolioOrder);
            PortfolioImgEntity[] portfolioImgEntity = this.manageService.portfolioImgEntity(portfolioOrder);

            modelAndView.addObject("portfolioOrder", portfolioEntity.getPortfolioOrder());
            modelAndView.addObject("email", portfolioEntity.getEmail());
            modelAndView.addObject("portfolioTitle", portfolioEntity.getPortfolioTitle());
            modelAndView.addObject("portfolioAdDetailCategory", portfolioEntity.getPortfolioAdDetailCategory());
            modelAndView.addObject("portfolioAdCategory", portfolioEntity.getPortfolioAdCategory());
            modelAndView.addObject("portfolioRegion", portfolioEntity.getPortfolioRegion());
            modelAndView.addObject("mainImgNm", portfolioEntity.getMainImgNm());
            modelAndView.addObject("mainOriginFileNm", portfolioEntity.getMainOriginFileNm());
            modelAndView.addObject("mainImgFilePath", portfolioEntity.getMainImgFilePath());
            modelAndView.addObject("createDt", portfolioEntity.getCreateDt());
            modelAndView.addObject("modifyDt", portfolioEntity.getModifyDt());

            modelAndView.addObject("portfolioImgEntity", portfolioImgEntity);
        } else {
            modelAndView =  new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @PatchMapping("/manage/portfolio/update")
    @ResponseBody
    public String managePortfolioUpdate(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, PortfolioEntity portfolioEntity) {
        JSONObject responseObject = new JSONObject();
        portfolioEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.managePortfolioUpdate(portfolioEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/manage/portfolio/change/thumbnail")
    @ResponseBody
    public String managePortfolioChangeThumbnail(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MultipartFile thumbnail, PortfolioEntity portfolioEntity) {
        JSONObject responseObject = new JSONObject();
        portfolioEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.managePortfolioChangeThumbnail(thumbnail, portfolioEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/manage/portfolio/detail/img/update")
    @ResponseBody
    public String managePortfolioDetailImgUpdate(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "existDetailImgArr", required = false) String existDetailImgArr, MultipartFile[] detailImgArr, PortfolioImgEntity portfolioImgEntity) throws Exception {
        JSONObject responseObject = new JSONObject();
        portfolioImgEntity.setEmail(joinVO.getEmail());

        JSONParser jp = new JSONParser();
        JSONArray ja = (JSONArray)jp.parse(existDetailImgArr);

        PortfolioImgEntity[] portfolioImgEntities = new PortfolioImgEntity[ja.size()];
        for(int i = 0; i < ja.size(); i++) {
            PortfolioImgEntity entity = new PortfolioImgEntity();
            JSONObject jo = (JSONObject) ja.get(i);
            entity.setPortfolioImgOrder(((Long) jo.get("portfolioImgOrder")).intValue());
            entity.setPortfolioOrder(((Long) jo.get("portfolioOrder")).intValue());
            entity.setEmail((String) jo.get("email"));
            entity.setImgNm((String) jo.get("imgNm"));
            entity.setOriginFileNm((String) jo.get("originFileNm"));
            entity.setImgFilePath((String) jo.get("imgFilePath"));

            portfolioImgEntities[i] = entity;
        }
        Enum<?> result = this.manageService.managePortfolioDetailImgUpdate(portfolioImgEntities, detailImgArr, portfolioImgEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @DeleteMapping("/manage/portfolio/delete")
    @ResponseBody
    public String managePortfolioDelete(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, PortfolioEntity portfolioEntity) {
        JSONObject responseObject = new JSONObject();
        portfolioEntity.setEmail(joinVO.getEmail());
        Enum<?> result = this.manageService.managePortfolioDelete(portfolioEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
