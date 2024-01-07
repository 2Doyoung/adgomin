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
                MediaRegisterEntity mediaRegisterEntityDetail = this.adminService.getMediaSubmitDetail(mediaOrder);
                if(mediaRegisterEntityDetail != null) {
                    modelAndView.addObject("mediaOrder", mediaRegisterEntityDetail.getMediaOrder());
                    modelAndView.addObject("email", mediaRegisterEntityDetail.getEmail());
                    modelAndView.addObject("adDetailCategory", mediaRegisterEntityDetail.getAdDetailCategory());
                    modelAndView.addObject("mediaTitle", mediaRegisterEntityDetail.getMediaTitle());
                    modelAndView.addObject("mediaSummary", mediaRegisterEntityDetail.getMediaSummary());
                    modelAndView.addObject("mediaDetailExplain", mediaRegisterEntityDetail.getMediaDetailExplain());
                    modelAndView.addObject("thumbnailImgNm", mediaRegisterEntityDetail.getThumbnailImgNm());
                    modelAndView.addObject("thumbnailOriginFileNm", mediaRegisterEntityDetail.getThumbnailOriginFileNm());
                    modelAndView.addObject("thumbnailImgFilePath", mediaRegisterEntityDetail.getThumbnailImgFilePath());
                    modelAndView.addObject("mediaPrice", mediaRegisterEntityDetail.getMediaPrice());
                    modelAndView.addObject("mediaSubmitStatus", mediaRegisterEntityDetail.getMediaSubmitStatus());
                    modelAndView.addObject("createDt", mediaRegisterEntityDetail.getCreateDt());
                    modelAndView.addObject("modifyDt", mediaRegisterEntityDetail.getModifyDt());
                    modelAndView.addObject("submitDt", mediaRegisterEntityDetail.getSubmitDt());
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
