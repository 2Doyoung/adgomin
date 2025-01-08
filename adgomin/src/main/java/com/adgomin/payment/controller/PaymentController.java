package com.adgomin.payment.controller;

import com.adgomin.email.service.EmailService;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.entity.PaymentEntity;
import com.adgomin.payment.service.PaymentService;
import com.adgomin.post.service.PostService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller(value = "com.adgomin.payment.controller.PaymentController")
public class PaymentController {
    private final PaymentService paymentService;

    private final PostService postService;

    private final EmailService emailService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String CLIENT_ID = "R2_6a8ed8d3f4314352bf6dab371ed932c3";
    private final String SECRET_KEY = "4d936c2a756c4969a8b3064579940c3a";
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
                modelAndView.addObject("buyerEmail", joinVO1.getEmail());
                modelAndView.addObject("buyerName", joinVO1.getName());
                modelAndView.addObject("buyerTel", joinVO1.getPhoneNumber());
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

    @RequestMapping("/serverAuth")
    public ModelAndView requestPayment(
            @RequestParam String tid,
            @RequestParam int amount,
            @RequestParam int mallReserved,
            Model model, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) throws Exception {
        ModelAndView modelAndView = null;

        MediaRegisterVO getPost = this.postService.getPost(String.valueOf(mallReserved));

        int mediaPriceAmount = Integer.parseInt(getPost.getMediaPrice().replace(",", ""));
        int mediaPriceCharge = (int) Math.floor(mediaPriceAmount * 0.05 / 10) * 10;
        int totalAmount = mediaPriceAmount + mediaPriceCharge;

        if(amount != totalAmount) {
            modelAndView = new ModelAndView("payment/fail");

            modelAndView.addObject("reason", "결제 중 불일치 정보가 감지되었습니다.");

            return modelAndView;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> AuthenticationMap = new HashMap<>();
        AuthenticationMap.put("amount", String.valueOf(amount));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.nicepay.co.kr/v1/payments/" + tid, request, JsonNode.class);

        JsonNode responseNode = responseEntity.getBody();
        String resultCode = responseNode.get("resultCode").asText();
        model.addAttribute("resultMsg", responseNode.get("resultMsg").asText());

        System.out.println(responseNode.toPrettyString());

        if (resultCode.equalsIgnoreCase("0000")) {
            modelAndView = new ModelAndView("payment/success");
        } else {
            modelAndView = new ModelAndView("payment/fail");
        }

        return modelAndView;
    }
}
