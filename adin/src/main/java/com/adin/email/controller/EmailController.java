package com.adin.email.controller;

import com.adin.email.service.EmailService;
import com.adin.join.entity.JoinEntity;
import org.codehaus.groovy.tools.shell.IO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller(value = "com.adin.email.controller.EmailController")
public class EmailController {
    @Autowired
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

            // Replace placeholders with actual values
            htmlContent = htmlContent.replace("{email}", email);
            htmlContent = htmlContent.replace("{certified}", certified);

            emailContent.append(htmlContent);
        } catch (IOException ioException) {

        }
        emailService.sendMail(email, "[애드인] 회원가입을 위해 이메일 인증을 해주세요.", emailContent.toString());

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

            // Replace placeholders with actual values
            htmlContent = htmlContent.replace("{email}", email);
            htmlContent = htmlContent.replace("{certified}", certified);

            emailContent.append(htmlContent);
        } catch (IOException ioException) {

        }
        emailService.sendMail(email, "[애드인] 회원가입을 위해 이메일 인증을 해주세요.", emailContent.toString());

        JSONObject responseObject = new JSONObject();
        responseObject.put("result", "success");

        return responseObject.toString();
    }
}
