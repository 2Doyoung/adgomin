package com.adgomin.email.controller;

import com.adgomin.email.service.EmailService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller(value = "com.adgomin.email.controller.EmailController")
public class EmailController {

    @Value("${app.url}")
    private String appUrl;

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) { this.emailService = emailService; }

    @GetMapping(value = "/sendEmail")
    @ResponseBody
    public String sendmail(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "certified", required = false) String certified) throws MessagingException {
        StringBuilder emailContent = new StringBuilder();
        try {
            ClassPathResource resource = new ClassPathResource("/templates/join/send_email.html");
            byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String htmlContent = new String(fileBytes, StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{email}", email);
            htmlContent = htmlContent.replace("{certified}", certified);
            htmlContent = htmlContent.replace("{appUrl}", appUrl);

            emailContent.append(htmlContent);
        } catch (IOException ioException) {

        }
        emailService.sendMail(email, "[애드곰인] 회원가입을 위해 이메일 인증을 해주세요.", emailContent.toString());

        JSONObject responseObject = new JSONObject();
        responseObject.put("result", "success");

        return responseObject.toString();
    }

    @GetMapping(value = "/reSendEmail")
    @ResponseBody
    public String reSendmail(@RequestParam(value = "email", required = false) String email) throws MessagingException {
        String certified = this.emailService.selectCertified(email);

        StringBuilder emailContent = new StringBuilder();
        try {
            ClassPathResource resource = new ClassPathResource("/templates/join/send_email.html");
            byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String htmlContent = new String(fileBytes, StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{email}", email);
            htmlContent = htmlContent.replace("{certified}", certified);
            htmlContent = htmlContent.replace("{appUrl}", appUrl);

            emailContent.append(htmlContent);
        } catch (IOException ioException) {

        }
        emailService.sendMail(email, "[애드곰인] 회원가입을 위해 이메일 인증을 해주세요.", emailContent.toString());

        JSONObject responseObject = new JSONObject();
        responseObject.put("result", "success");

        return responseObject.toString();
    }

    @GetMapping(value = "/passwordSendEmail")
    @ResponseBody
    public String passwordEmail(@RequestParam(value = "email", required = false) String email) throws MessagingException {
        String certified = this.emailService.selectCertified(email);

        StringBuilder emailContent = new StringBuilder();
        try {
            ClassPathResource resource = new ClassPathResource("/templates/join/password_send_email.html");
            byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String htmlContent = new String(fileBytes, StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{email}", email);
            htmlContent = htmlContent.replace("{certified}", certified);
            htmlContent = htmlContent.replace("{appUrl}", appUrl);

            emailContent.append(htmlContent);
        } catch (IOException ioException) {

        }
        emailService.sendMail(email, "[애드곰인] 비밀번호 변경 링크입니다.", emailContent.toString());

        JSONObject responseObject = new JSONObject();
        responseObject.put("result", "success");

        return responseObject.toString();
    }
}
