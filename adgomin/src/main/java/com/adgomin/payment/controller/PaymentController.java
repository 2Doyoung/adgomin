package com.adgomin.payment.controller;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.service.PaymentService;
import com.adgomin.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
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
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @GetMapping("/sendKakao")
    public String sendKakao() {
        String templateCode = "TU_2783"; // 사용하려는 템플릿 코드
        return paymentService.sendKakaoNotification(templateCode);
    }
}
