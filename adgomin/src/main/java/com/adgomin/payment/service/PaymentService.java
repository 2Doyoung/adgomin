package com.adgomin.payment.service;

import com.adgomin.payment.mapper.PaymentMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service(value = "com.adgomin.payment.service.PaymentService")
public class PaymentService {
    private final String apiKey = "mfnu5yj7kvlv6glkqyu0ohmm5djept3o"; // 발급받은 API Key
    private final String userId = "adgomin"; // 알리고 계정 ID
    private final String apiUrl = "https://kakaoapi.aligo.in/akv10/alimtalk/send/";
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    public String sendKakaoNotification(String templateCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String message = "애드곰인 인증번호를 입력해주세요.\n" +
                "[123456]";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userid", userId);
        params.add("apikey", apiKey);
        params.add("senderkey", "72cea54ce70f33de15fb0c9be988becabfa9c55a");
        params.add("sender", "01095129725");
        params.add("receiver_1", "01095129725");
        params.add("subject_1", "본인인증");
        params.add("message_1",  message);
        params.add("tpl_code", templateCode);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}
