package com.adin.join.service;

import com.adin.join.entity.JoinEntity;
import com.adin.join.entity.LoginLogEntity;
import com.adin.join.mapper.JoinMapper;
import com.adin.join.vo.JoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adin.enums.CommonResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

@Service(value = "com.adin.join.service.JoinService")
public class JoinService {
    private static final String COOKIE = "adinEncryptionCookie";
    private static final String SALT = "adinEncryptionSalt";
    private final JoinMapper joinMapper;

    @Autowired
    public JoinService(JoinMapper joinMapper) {
        this.joinMapper = joinMapper;
    }

    /**
     * 암호 인코더/디코더
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Enum<?> join(JoinEntity joinEntity) {
        String encodePassword = passwordEncoder.encode(joinEntity.getPassword());
        joinEntity.setPassword(encodePassword);
        joinEntity.setNickname(joinEntity.getEmail());
        joinEntity.setCertified(certifiedKey());

        return this.joinMapper.join(joinEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> emailCheck(String email) {
        JoinVO joinVO = this.joinMapper.emailCheck(email);

        return joinVO.getCount() == 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> login(JoinEntity joinEntity, HttpServletRequest request, Boolean rememberMe, HttpServletResponse response) {
        JoinVO joinVO = this.joinMapper.loginUser(joinEntity);

        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogEntity.setEmail(joinEntity.getEmail());
        loginLogEntity.setLoginTime(new Date());

        if(joinVO.getCount() == 0) {
            loginLogEntity.setStatus("EMAIL_NOT_EXIST");
            this.joinMapper.insertLoginLog(loginLogEntity);
            return CommonResult.EMAIL_NOT_EXIST;
        }

        boolean passwordCheck = passwordEncoder.matches(joinEntity.getPassword(), joinVO.getPassword());

        if(!passwordCheck) {
            loginLogEntity.setStatus("PASSWORD_ERROR");
            this.joinMapper.insertLoginLog(loginLogEntity);
            return CommonResult.PASSWORD_ERROR;
        }

        if("0".equals(joinVO.getUseYn())) {
            loginLogEntity.setStatus("SECESSION_USER");
            this.joinMapper.insertLoginLog(loginLogEntity);
            return CommonResult.SECESSION_USER;
        }

        if("0".equals(joinVO.getCertifiedYn())) {
            loginLogEntity.setStatus("NO_CERTIFIED_USER");
            this.joinMapper.insertLoginLog(loginLogEntity);
            return  CommonResult.NO_CERTIFIED_USER;
        }
        loginLogEntity.setStatus("SUCCESS");
        this.joinMapper.insertLoginLog(loginLogEntity);

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", joinVO);

        if(rememberMe) {
            byte[] cookieBytes = COOKIE.getBytes();
            byte[] saltBytes = SALT.getBytes();

            TextEncryptor encryptor = Encryptors.text(new String(Hex.encode(cookieBytes)), new String(Hex.encode(saltBytes)));
            String encryptedEmail = encryptor.encrypt(joinVO.getEmail());
            Cookie cookie = new Cookie("REMEMBER", encryptedEmail);
            cookie.setMaxAge(365 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return CommonResult.SUCCESS;
    }

    private String certifiedKey() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {

            }
        } while(sb.length() < 10);
        return sb.toString();
    }

    public Enum<?> certifiedCheck(String email, String certified, HttpServletRequest request) {
        JoinVO joinVO = this.joinMapper.certifiedCheck(email, certified);

        if(joinVO.getCount() > 0) {
            this.joinMapper.certifiedUpdate(email);

            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_USER", joinVO);
            return CommonResult.SUCCESS;
        }

        return CommonResult.FAILURE;
    }
}
