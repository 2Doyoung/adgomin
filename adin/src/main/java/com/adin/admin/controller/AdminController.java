package com.adin.admin.controller;

import com.adin.admin.service.AdminService;
import com.adin.common.Criteria;
import com.adin.common.Paging;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

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
        if(manage == null) {
            if(joinVO != null) {
                if(joinVO.getUserType().equals("admin")) {
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
                } else {
                    modelAndView = new ModelAndView("error/error");
                }
            } else {
                modelAndView = new ModelAndView("error/error");
            }
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
}
