package com.adgomin.payment.controller;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.entity.PaymentEntity;
import com.adgomin.payment.service.PaymentService;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.post.service.PostService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Locale;

@Controller(value = "com.adgomin.payment.controller.PaymentController")
public class PaymentController {
    private final PaymentService paymentService;

    private final PostService postService;

    public PaymentController(PaymentService paymentService, PostService postService) {
        this.paymentService = paymentService;
        this.postService = postService;
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

    @RequestMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };

        PaymentEntity getTotalAmount = this.paymentService.getTotalAmount(orderId);

        if(getTotalAmount.getTotalAmount() != Integer.parseInt(amount)) {
            JSONObject failResponse = new JSONObject();
            failResponse.put("message", "결제 금액이 일치하지 않습니다.");
            failResponse.put("code", "AMOUNT_MISMATCH");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failResponse);
        }

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "test_gsk_vZnjEJeQVxmwygKb4EYZrPmOoBN0";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }

    @PostMapping("/payment/register")
    @ResponseBody
    public String paymentRegister(@SessionAttribute(name = "LOGIN_USER", required = false) JoinVO joinVO, PaymentEntity paymentEntity) {
        JSONObject responseObject = new JSONObject();
        Enum<?> result = this.paymentService.paymentRegister(joinVO, paymentEntity);
        responseObject.put("result", result.name().toLowerCase());

        return responseObject.toString();
    }
}
