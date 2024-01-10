package com.adin.admin.controller;

import com.adin.admin.service.AdminService;
import com.adin.common.Criteria;
import com.adin.common.Paging;
import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.vo.MediaRegisterVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller(value = "com.adin.admin.controller.AdminController")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/admin")
    public ModelAndView admin(@RequestParam(value = "manage", required = false) String manage, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "page", required = false) int page) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            if(joinVO.getUserType().equals("admin")) {
                if(manage == null) {
                    modelAndView =  new ModelAndView("admin/admin_submit_list");

                    MediaRegisterVO mediaRegisterVO = this.adminService.getMediaSubmitListCnt();

                    int cnt = mediaRegisterVO.getCnt();
                    Criteria cri = new Criteria();
                    cri.setPage(page);

                    Paging paging = new Paging();
                    paging.setCri(cri);
                    paging.setTotalCount(cnt);

                    int pageStart = cri.getPageStart();
                    int perPageNum = cri.getPerPageNum();

                    MediaRegisterEntity[] mediaRegisterEntityList = this.adminService.getMediaSubmitList(pageStart, perPageNum);
                    modelAndView.addObject("mediaRegisterEntityList", mediaRegisterEntityList);
                    modelAndView.addObject("paging", paging);
                } else if("mediaUserList".equals(manage)) {
                    modelAndView =  new ModelAndView("admin/admin_media_user_list");

                    JoinVO joinVO2 = this.adminService.getMediaUserListCnt();

                    int cnt = joinVO2.getCount();
                    Criteria cri = new Criteria();
                    cri.setPage(page);

                    Paging paging = new Paging();
                    paging.setCri(cri);
                    paging.setTotalCount(cnt);

                    int pageStart = cri.getPageStart();
                    int perPageNum = cri.getPerPageNum();

                    JoinEntity[] joinEntityList = this.adminService.getMediaUserList(pageStart, perPageNum);
                    modelAndView.addObject("joinEntityList", joinEntityList);
                    modelAndView.addObject("paging", paging);
                } else if("advertiserUserList".equals(manage)) {
                    modelAndView =  new ModelAndView("admin/admin_advertiser_user_list");

                    JoinVO joinVO2 = this.adminService.getAdvertiserUserListCnt();

                    int cnt = joinVO2.getCount();
                    Criteria cri = new Criteria();
                    cri.setPage(page);

                    Paging paging = new Paging();
                    paging.setCri(cri);
                    paging.setTotalCount(cnt);

                    int pageStart = cri.getPageStart();
                    int perPageNum = cri.getPerPageNum();

                    JoinEntity[] joinEntityList = this.adminService.getAdvertiserUserList(pageStart, perPageNum);
                    modelAndView.addObject("joinEntityList", joinEntityList);
                    modelAndView.addObject("paging", paging);
                }
            } else {
                modelAndView = new ModelAndView("error/error");
            }
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @GetMapping(value = "/admin/adminSubmitDetail")
    public ModelAndView adminSubmitDetail(@RequestParam(value = "mediaOrder", required = false) int mediaOrder, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            if(joinVO.getUserType().equals("admin")) {
                modelAndView = new ModelAndView("admin/admin_submit_detail");
                MediaRegisterVO mediaRegisterVoDetail = this.adminService.getMediaSubmitDetail(mediaOrder);
                if(mediaRegisterVoDetail != null) {
                    modelAndView.addObject("mediaOrder", mediaRegisterVoDetail.getMediaOrder());
                    modelAndView.addObject("email", mediaRegisterVoDetail.getEmail());
                    modelAndView.addObject("adDetailCategory", mediaRegisterVoDetail.getAdDetailCategory());
                    modelAndView.addObject("mediaTitle", mediaRegisterVoDetail.getMediaTitle());
                    modelAndView.addObject("mediaSummary", mediaRegisterVoDetail.getMediaSummary());
                    modelAndView.addObject("mediaDetailExplain", mediaRegisterVoDetail.getMediaDetailExplain());
                    modelAndView.addObject("thumbnailImgNm", mediaRegisterVoDetail.getThumbnailImgNm());
                    modelAndView.addObject("thumbnailOriginFileNm", mediaRegisterVoDetail.getThumbnailOriginFileNm());
                    modelAndView.addObject("thumbnailImgFilePath", mediaRegisterVoDetail.getThumbnailImgFilePath());
                    modelAndView.addObject("mediaPrice", mediaRegisterVoDetail.getMediaPrice());
                    modelAndView.addObject("mediaSubmitStatus", mediaRegisterVoDetail.getMediaSubmitStatus());
                    modelAndView.addObject("createDt", mediaRegisterVoDetail.getCreateDt());
                    modelAndView.addObject("modifyDt", mediaRegisterVoDetail.getModifyDt());
                    modelAndView.addObject("submitDt", mediaRegisterVoDetail.getSubmitDt());
                    modelAndView.addObject("nickname", mediaRegisterVoDetail.getNickname());
                    modelAndView.addObject("mediaIntroduce", mediaRegisterVoDetail.getMediaIntroduce());
                    modelAndView.addObject("region", mediaRegisterVoDetail.getRegion());
                    modelAndView.addObject("adCategory", mediaRegisterVoDetail.getAdCategory());
                    modelAndView.addObject("mediaUrl", mediaRegisterVoDetail.getMediaUrl());
                } else {
                    modelAndView = new ModelAndView("error/error");
                }
            } else {
                modelAndView = new ModelAndView("error/error");
            }
        } else {
            modelAndView = new ModelAndView("error/error");
        }


        return  modelAndView;
    }

    @PatchMapping("/admin/judgeComplete")
    @ResponseBody
    public String judgeComplete(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, MediaRegisterEntity mediaRegisterEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.adminService.judgeComplete(mediaRegisterEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @GetMapping("/submit/thumbnail/image")
    public ResponseEntity<Resource> submitThumbnailImage(@RequestParam(value = "mediaOrder") String mediaOrder) {
        MediaRegisterEntity mediaRegisterEntity = this.adminService.submitThumbnailImage(mediaOrder);
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
}
