package com.adgomin.payment.controller;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.service.PaymentService;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.post.service.PostService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adgomin.payment.controller.PaymentController")
public class PaymentController {
    private final PaymentService paymentService;

    private final PostService postService;

    public PaymentController(PaymentService paymentService, PostService postService) {
        this.paymentService = paymentService;
        this.postService = postService;
    }

    @GetMapping(value = "/payment")
    public ModelAndView media(@RequestParam(value = "mediaOrder", required = false) String mediaOrder, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView =  new ModelAndView("payment/payment");

            MediaRegisterVO getPost = this.postService.getPost(mediaOrder);
            JoinVO joinVO1 = this.paymentService.getVerificationInfo(joinVO);

            modelAndView.addObject("mediaOrder", mediaOrder);
            modelAndView.addObject("adDetailCategory", getPost.getAdDetailCategory());
            modelAndView.addObject("mediaTitle", getPost.getMediaTitle());
            modelAndView.addObject("mediaSummary", getPost.getMediaSummary());
            modelAndView.addObject("mediaDetailExplain", getPost.getMediaDetailExplain());
            modelAndView.addObject("thumbnailImgNm", getPost.getThumbnailImgNm());
            modelAndView.addObject("thumbnailOriginFileNm", getPost.getThumbnailOriginFileNm());
            modelAndView.addObject("thumbnailImgFilePath", getPost.getThumbnailImgFilePath());
            modelAndView.addObject("mediaPrice", getPost.getMediaPrice());
            modelAndView.addObject("mediaIntroduce", getPost.getMediaIntroduce());
            modelAndView.addObject("region", getPost.getRegion());
            modelAndView.addObject("adCategory", getPost.getAdCategory());
            modelAndView.addObject("nickname", getPost.getNickname());
            modelAndView.addObject("email", getPost.getEmail());
            modelAndView.addObject("userOrder", getPost.getUserOrder());
            modelAndView.addObject("phoneNumberYn", joinVO1.getPhoneNumberYn());
            modelAndView.addObject("offerPeriod", getPost.getOfferPeriod());
            if("1".equals(joinVO1.getPhoneNumberYn())) {
                modelAndView.addObject("email", joinVO1.getEmail());
                modelAndView.addObject("name", joinVO1.getName());
                modelAndView.addObject("phoneNumber", joinVO1.getPhoneNumber());
            }
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @GetMapping("/send/kakao")
    @ResponseBody
    public String sendKakao(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        paymentService.sendKakaoNotification(joinVO, phoneNumber);
        JSONObject responseObject = new JSONObject();
        responseObject.put("result", "success");

        return responseObject.toString();
    }

    @PostMapping("/phone/number/certification/check")
    @ResponseBody
    public String phoneNumberCertificationCheck(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, @RequestParam(value = "phoneNumberCertification") String phoneNumberCertification) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.paymentService.phoneNumberCertificationCheck(joinVO, phoneNumberCertification);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @PatchMapping("/phone/number/certification/success")
    @ResponseBody
    public String phoneNumberCertificationSuccess(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, JoinEntity joinEntity) {
        JSONObject responseObject = new JSONObject();
        joinEntity.setUserOrder(joinVO.getUserOrder());
        Enum<?> result = this.paymentService.phoneNumberCertificationSuccess(joinEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
