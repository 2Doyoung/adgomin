package com.adgomin.user.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.mapper.JoinMapper;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.user.entity.SecessionReasonEntity;
import com.adgomin.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service(value = "com.adgomin.user.service.UserService")
@Transactional
public class UserService {
    private final UserMapper userMapper;
    private final JoinMapper joinMapper;

    @Autowired
    public UserService(UserMapper userMapper, JoinMapper joinMapper) { this.userMapper = userMapper;
        this.joinMapper = joinMapper;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Enum<?> changeProfileImg(JoinVO joinVO, MultipartFile profileFile) {
        JoinEntity userOrder = this.joinMapper.userOrder(joinVO.getEmail());
        String path = "/home/ec2-user/profile/" + userOrder.getUserOrder();

        String originalFilename = profileFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String storeFilename = UUID.randomUUID() + "." + extension;

        File file = new File(path);

        if(!file.exists()) {
            file.mkdirs();
        }

        try {
            Path absolutePath = Paths.get(path  + "/" + storeFilename).toAbsolutePath();
            profileFile.transferTo(absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        joinVO.setProfileOriginFileNm(profileFile.getOriginalFilename());
        joinVO.setProfileImgNm(storeFilename);
        joinVO.setProfileImgFilePath(path);

        return this.userMapper.changeProfileImg(joinVO) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public JoinVO profileImage(int userOrder) {
        return this.userMapper.profileImage(userOrder);
    }

    public Enum<?> changeNickname(JoinVO joinVO) {
        return this.userMapper.changeNickname(joinVO) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> passwordCheck(JoinVO joinVO, String password) {
        boolean passwordCheck = passwordEncoder.matches(password, joinVO.getPassword());

        return passwordCheck ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> userPasswordChange(JoinVO joinVO, String password) {
        String encodePassword = passwordEncoder.encode(password);
        joinVO.setPassword(encodePassword);

        return this.userMapper.userPasswordChange(joinVO) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> userSecession(JoinVO joinVO) {
        return this.userMapper.userSecession(joinVO) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> userSecessionReason(JoinVO joinVO, String secessionReason) {
        SecessionReasonEntity secessionReasonEntity = new SecessionReasonEntity();
        secessionReasonEntity.setReason(secessionReason);
        secessionReasonEntity.setEmail(joinVO.getEmail());
        return this.userMapper.userSecessionReason(secessionReasonEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
