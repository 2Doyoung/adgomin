package com.adin.user.service;

import com.adin.enums.CommonResult;
import com.adin.join.vo.JoinVO;
import com.adin.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service(value = "com.adin.user.service.UserService")
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) { this.userMapper = userMapper; }

    public Enum<?> changeProfileImg(JoinVO joinVO, MultipartFile profileFile) {
        String path = "../profile/" + joinVO.getEmail();

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

    public JoinVO profileImage(String email) {
        return this.userMapper.profileImage(email);
    }
}
