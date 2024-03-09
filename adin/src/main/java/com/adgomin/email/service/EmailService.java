package com.adgomin.email.service;

import com.adgomin.email.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service(value = "com.adgomin.email.service.EmailService")
@Transactional
public class EmailService {
    private EmailMapper emailMapper;

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(EmailMapper emailMapper, JavaMailSender javaMailSender) {
        this.emailMapper = emailMapper;
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom("adgomincorpservice@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(message, true);

        javaMailSender.send(mimeMessage);
    }

    public String selectCertified(String email) {
        String certified = this.emailMapper.selectCertified(email);

        return certified;
    }
}
