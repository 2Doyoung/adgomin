package com.adgomin.payment.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.payment.entity.*;
import com.adgomin.payment.mapper.PaymentMapper;
import com.adgomin.post.mapper.PostMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service(value = "com.adgomin.payment.service.PaymentService")
public class PaymentService {
    private final String apiKey = "g8uqfwdzx7swxj3sk6mbzlnb2cu4zmro"; // 발급받은 API Key
    private final String userId = "adgomin"; // 알리고 계정 ID
    private final String apiUrl = "https://kakaoapi.aligo.in/akv10/alimtalk/send/";
    private final PaymentMapper paymentMapper;
    private final PostMapper postMapper;

    public PaymentService(PaymentMapper paymentMapper, PostMapper postMapper) {
        this.paymentMapper = paymentMapper;
        this.postMapper = postMapper;
    }

    public String sendKakaoNotification(JoinVO joinVO, String phoneNumber) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Random random = new Random();
        int authCode = 100000 + random.nextInt(900000);

        joinVO.setPhoneNumberCertification(String.valueOf(authCode));

        this.paymentMapper.setPhoneNumberCertification(joinVO);

        String templateCode = "TU_2783";
        String message = "애드곰인 인증번호를 입력해주세요.\n" +
                "[" + authCode + "]";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userid", userId);
        params.add("apikey", apiKey);
        params.add("senderkey", "72cea54ce70f33de15fb0c9be988becabfa9c55a");
        params.add("sender", "01095129725");
        params.add("receiver_1", phoneNumber);
        params.add("subject_1", "본인인증");
        params.add("message_1",  message);
        params.add("tpl_code", templateCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    public Enum<?> phoneNumberCertificationCheck(JoinVO joinVO, String phoneNumberCertification) {
        JoinVO joinVO1 = this.paymentMapper.phoneNumberCertificationCheck(joinVO.getUserOrder(), phoneNumberCertification);
        Enum<?> result = CommonResult.SUCCESS;

        if(joinVO1.getCount() == 0) {
            result = CommonResult.FAILURE;
        }

        return result;
    }

    public JoinVO getVerificationInfo(JoinVO joinVO) {
        return this.paymentMapper.getVerificationInfo(joinVO.getUserOrder());
    }

    public Enum<?> phoneNumberCertificationSuccess(JoinEntity joinEntity) {
        return this.paymentMapper.phoneNumberCertificationSuccess(joinEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> paymentRegister(JoinVO joinVO, PaymentsEntity paymentsEntity) {
        JoinVO sellerOrder = this.paymentMapper.getSellerOrder(paymentsEntity.getMediaOrder());
        MediaRegisterVO getPost = this.postMapper.getPost(String.valueOf(paymentsEntity.getMediaOrder()));
        int mediaPriceAmount = Integer.parseInt(getPost.getMediaPrice().replace(",", ""));
        int mediaPriceCharge = (int) Math.floor(mediaPriceAmount * 0.05 / 10) * 10;
        int totalAmount = mediaPriceAmount + mediaPriceCharge;

        paymentsEntity.setBuyerOrder(joinVO.getUserOrder());
        paymentsEntity.setSellerOrder(sellerOrder.getUserOrder());
        paymentsEntity.setTotalAmount(totalAmount);

        return this.paymentMapper.paymentRegister(paymentsEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public void insertPaymentFailed(PaymentFailedEntity paymentFailedEntity) {
        this.paymentMapper.insertPaymentFailed(paymentFailedEntity);
    }

    public void insertPayments(PaymentsEntity paymentsEntity) {
        this.paymentMapper.insertPayments(paymentsEntity);
    }

    public void insertPaymentsCard(PaymentsCardEntity paymentsCardEntity) {
        this.paymentMapper.insertPaymentsCard(paymentsCardEntity);
    }

    public void insertPaymentsBank(PaymentsBankEntity paymentsBankEntity) {
        this.paymentMapper.insertPaymentsBank(paymentsBankEntity);
    }

    public void insertPaymentsVbank(PaymentsVbankEntity paymentsVbankEntity) {
        this.paymentMapper.insertPaymentsVbank(paymentsVbankEntity);
    }
}
