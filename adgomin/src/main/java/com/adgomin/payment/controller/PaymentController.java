package com.adgomin.payment.controller;

import com.adgomin.email.service.EmailService;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.entity.PaymentEntity;
import com.adgomin.payment.service.PaymentService;
import com.adgomin.post.service.PostService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;

@Controller(value = "com.adgomin.payment.controller.PaymentController")
public class PaymentController {
    private final PaymentService paymentService;

    private final PostService postService;

    private final EmailService emailService;
    @Value("${app.url}")
    private String appUrl;

    public PaymentController(PaymentService paymentService, PostService postService, EmailService emailService) {
        this.paymentService = paymentService;
        this.postService = postService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/payment")
    public ModelAndView payment(@RequestParam(value = "mediaOrder", required = false) String mediaOrder, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView =  new ModelAndView("payment/payment");

            MediaRegisterVO getPost = this.postService.getPost(mediaOrder);
            JoinVO joinVO1 = this.paymentService.getVerificationInfo(joinVO);

            int mediaPriceAmount = Integer.parseInt(getPost.getMediaPrice().replace(",", ""));
            int mediaPriceCharge = (int) Math.floor(mediaPriceAmount * 0.05 / 10) * 10;
            String formattedMediaPriceCharge = NumberFormat.getNumberInstance(Locale.getDefault()).format(mediaPriceCharge);
            int totalAmount = mediaPriceAmount + mediaPriceCharge;
            String formattedTotalAmount = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalAmount);

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
            modelAndView.addObject("formattedMediaPriceCharge", formattedMediaPriceCharge);
            modelAndView.addObject("formattedTotalAmount", formattedTotalAmount);
            modelAndView.addObject("totalAmount", totalAmount);
            modelAndView.addObject("sessionEmail", joinVO.getEmail());
            if("1".equals(joinVO1.getPhoneNumberYn())) {
                modelAndView.addObject("customerEmail", joinVO1.getEmail());
                modelAndView.addObject("customerName", joinVO1.getName());
                modelAndView.addObject("customerPhoneNumber", joinVO1.getPhoneNumber());
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

    @GetMapping(value = "/success")
    public ModelAndView success(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView = new ModelAndView("payment/success");
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @GetMapping(value = "/fail")
    public ModelAndView fail(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) {
        ModelAndView modelAndView = null;
        if(joinVO != null) {
            modelAndView = new ModelAndView("payment/fail");
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        return  modelAndView;
    }

    @PostMapping("/payment/register")
    @ResponseBody
    public String paymentRegister(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, PaymentEntity paymentEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.paymentService.paymentRegister(joinVO, paymentEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }

    @GetMapping(value = "/phoneNumberSendEmail")
    @ResponseBody
    public String phoneNumberSendEmail(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) throws MessagingException {
        StringBuilder emailContent = new StringBuilder();
        try {
            ClassPathResource resource = new ClassPathResource("/templates/payment/phoneNumberSuccess.html");
            byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String htmlContent = new String(fileBytes, StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{email}", joinVO.getEmail());
            htmlContent = htmlContent.replace("{appUrl}", appUrl);

            emailContent.append(htmlContent);
        } catch (IOException ioException) {

        }
        emailService.sendMail(joinVO.getEmail(), "[애드곰인] 본인인증 상태 변경 안내입니다.", emailContent.toString());

        JSONObject responseObject = new JSONObject();
        responseObject.put("result", "success");

        return responseObject.toString();
    }
}
