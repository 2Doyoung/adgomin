package com.adgomin.payment.controller;

import com.adgomin.email.service.EmailService;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.entity.*;
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
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public String paymentRegister(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, PaymentsEntity paymentsEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.paymentService.paymentRegister(joinVO, paymentsEntity);
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
            @RequestParam String authResultCode,
            @RequestParam String authResultMsg,
            @RequestParam String clientId,
            @RequestParam String orderId,
            @RequestParam String authToken,
            @RequestParam String signature,
            Model model, @SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO) throws Exception {
        ModelAndView modelAndView = null;

        MediaRegisterVO getPost = this.postService.getPost(String.valueOf(mallReserved));

        int mediaPriceAmount = Integer.parseInt(getPost.getMediaPrice().replace(",", ""));
        int mediaPriceCharge = (int) Math.floor(mediaPriceAmount * 0.05 / 10) * 10;
        int totalAmount = mediaPriceAmount + mediaPriceCharge;

        String clientSignatureTest = generateClientSignature(authToken, clientId, amount);

        if(!authResultCode.equals("0000")) {
            modelAndView = new ModelAndView("payment/fail");

            modelAndView.addObject("reason", "결제 중 불일치 정보가 감지되었습니다.");

            return modelAndView;
        }

        if(!signature.equals(clientSignatureTest)) {
            modelAndView = new ModelAndView("payment/fail");

            modelAndView.addObject("reason", "결제 중 불일치 정보가 감지되었습니다.");

            return modelAndView;
        }

        if(amount != totalAmount) {
            PaymentFailedEntity paymentFailedEntity = new PaymentFailedEntity();
            paymentFailedEntity.setBuyerOrder(joinVO.getUserOrder());
            paymentFailedEntity.setSellerOrder(getPost.getUserOrder());
            paymentFailedEntity.setMediaOrder(mallReserved);
            paymentFailedEntity.setTotalAmount(totalAmount);
            paymentFailedEntity.setPaymentAmount(amount);
            paymentFailedEntity.setPaymentFailedReason("결제금액 불일치");

            this.paymentService.insertPaymentFailed(paymentFailedEntity);

            modelAndView = new ModelAndView("payment/fail");

            modelAndView.addObject("reason", "결제 중 불일치 정보가 감지되었습니다.");

            return modelAndView;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String ediDate = sdf.format(new Date());

        String signData = generateServerSignature(tid, amount, ediDate);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + SECRET_KEY).getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> AuthenticationMap = new HashMap<>();
        AuthenticationMap.put("amount", String.valueOf(amount));
        AuthenticationMap.put("tid", tid);
        AuthenticationMap.put("ediDate", ediDate);
        AuthenticationMap.put("signData", signData);
        AuthenticationMap.put("returnCharSet", "utf-8");

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(AuthenticationMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.nicepay.co.kr/v1/payments/" + tid, request, JsonNode.class);

        JsonNode responseNode = responseEntity.getBody();
        String resultCode = responseNode.get("resultCode").asText();
        model.addAttribute("resultMsg", responseNode.get("resultMsg").asText());

        System.out.println(responseNode.toPrettyString());

        String responseSignData = responseNode.get("signature").asText();
        String responseTid = responseNode.get("tid").asText();
        int responseAmount = responseNode.get("amount").asInt();
        String responseEdiDate = responseNode.get("ediDate").asText();

        String expectedSignData = generateServerSignature(responseTid, responseAmount, responseEdiDate);

        if (!expectedSignData.equals(responseSignData)) {
            modelAndView = new ModelAndView("payment/fail");

            modelAndView.addObject("reason", "결제 중 불일치 정보가 감지되었습니다.");

            return modelAndView;
        }

        if (resultCode.equalsIgnoreCase("0000")) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime dateTime = ZonedDateTime.parse(responseNode.get("paidAt").asText(), inputFormatter);
            String paidAt = dateTime.format(outputFormatter);

            PaymentsEntity paymentsEntity = new PaymentsEntity();
            paymentsEntity.setOrderId(responseNode.get("orderId").asText());
            paymentsEntity.setBuyerOrder(joinVO.getUserOrder());
            paymentsEntity.setSellerOrder(getPost.getUserOrder());
            paymentsEntity.setMediaOrder(mallReserved);
            paymentsEntity.setTotalAmount(totalAmount);
            paymentsEntity.setPaymentAmount(amount);
            paymentsEntity.setTid(responseNode.get("tid").asText());
            paymentsEntity.setSignature(responseNode.get("signature").asText());
            paymentsEntity.setStatus(responseNode.get("status").asText());
            paymentsEntity.setPaidAt(paidAt);
            paymentsEntity.setPayMethod(responseNode.get("payMethod").asText());
            paymentsEntity.setGoodsName(responseNode.get("goodsName").asText());
            paymentsEntity.setApproveNo(responseNode.get("approveNo").asText());
            paymentsEntity.setBuyerName(responseNode.get("buyerName").asText());
            paymentsEntity.setBuyerTel(responseNode.get("buyerTel").asText());
            paymentsEntity.setBuyerEmail(responseNode.get("buyerEmail").asText());
            paymentsEntity.setReceiptUrl(responseNode.get("receiptUrl").asText());

            this.paymentService.insertPayments(paymentsEntity);

            int paymentOrder = paymentsEntity.getPaymentOrder();

            if(responseNode.get("payMethod").asText().equals("card") || responseNode.get("payMethod").asText().equals("kakaopay")
            || responseNode.get("payMethod").asText().equals("naverpay")) {
                PaymentsCardEntity paymentsCardEntity = new PaymentsCardEntity();

                paymentsCardEntity.setPaymentOrder(paymentOrder);
                paymentsCardEntity.setCardCode(responseNode.get("card").get("cardCode").asText());
                paymentsCardEntity.setCardName(responseNode.get("card").get("cardName").asText());
                paymentsCardEntity.setCardNum(responseNode.get("card").get("cardNum").asText());
                paymentsCardEntity.setCardQuota(responseNode.get("card").get("cardQuota").asText());
                paymentsCardEntity.setCardType(responseNode.get("card").get("cardType").asText());
                paymentsCardEntity.setCanPartCancel(responseNode.get("card").get("canPartCancel").asText());
                paymentsCardEntity.setAcquCardCode(responseNode.get("card").get("acquCardCode").asText());
                paymentsCardEntity.setAcquCardName(responseNode.get("card").get("acquCardName").asText());

                this.paymentService.insertPaymentsCard(paymentsCardEntity);
            } else if(responseNode.get("payMethod").asText().equals("bank")) {
                PaymentsBankEntity paymentsBankEntity = new PaymentsBankEntity();

                paymentsBankEntity.setPaymentOrder(paymentOrder);
                paymentsBankEntity.setBankCode(responseNode.get("bank").get("bankCode").asText());
                paymentsBankEntity.setBankName(responseNode.get("bank").get("bankName").asText());

                this.paymentService.insertPaymentsBank(paymentsBankEntity);
            } else if(responseNode.get("payMethod").asText().equals("vbank")) {
                DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                DateTimeFormatter outputFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                ZonedDateTime dateTime2 = ZonedDateTime.parse(responseNode.get("vbank").get("vbankExpDate").asText(), inputFormatter2);
                String vbankExpDate = dateTime2.format(outputFormatter2);

                PaymentsVbankEntity paymentsVbankEntity = new PaymentsVbankEntity();

                paymentsVbankEntity.setPaymentOrder(paymentOrder);
                paymentsVbankEntity.setVbankCode(responseNode.get("vbank").get("vbankCode").asText());
                paymentsVbankEntity.setVbankName(responseNode.get("vbank").get("vbankName").asText());
                paymentsVbankEntity.setVbankNumber(responseNode.get("vbank").get("vbankNumber").asText());
                paymentsVbankEntity.setVbankExpDate(vbankExpDate);
                paymentsVbankEntity.setVbankHolder(responseNode.get("vbank").get("vbankHolder").asText());

                this.paymentService.insertPaymentsVbank(paymentsVbankEntity);
            }

            if(responseNode.get("payMethod").asText().equals("card") || responseNode.get("payMethod").asText().equals("kakaopay")
                    || responseNode.get("payMethod").asText().equals("naverpay")) {
                modelAndView = new ModelAndView("payment/success");
            } else if(responseNode.get("payMethod").asText().equals("vbank")) {
                modelAndView = new ModelAndView("payment/success_vbank");
            }

            modelAndView.addObject("totalAmount", totalAmount);
            modelAndView.addObject("mediaPriceCharge", mediaPriceCharge);
            modelAndView.addObject("mediaTitle", getPost.getMediaTitle());
            modelAndView.addObject("userOrder", getPost.getUserOrder());
            modelAndView.addObject("nickname", getPost.getNickname());
            modelAndView.addObject("mediaPrice", getPost.getMediaPrice());
            modelAndView.addObject("thumbnailImgNm", getPost.getThumbnailImgNm());
            modelAndView.addObject("thumbnailImgFilePath", getPost.getThumbnailImgFilePath());
            modelAndView.addObject("orderId", responseNode.get("orderId").asText());
            modelAndView.addObject("receiptUrl", responseNode.get("receiptUrl").asText());
        } else {
            modelAndView = new ModelAndView("payment/fail");
        }

        return modelAndView;
    }

    public String generateClientSignature(String authToken, String clientId, int amount) throws Exception {
        String rawData = authToken + clientId + amount + SECRET_KEY;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public String generateServerSignature(String tid, int amount, String ediDate) throws Exception {
        String rawData = tid + amount + ediDate + SECRET_KEY;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
