package com.adgomin.manage.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.mapper.JoinMapper;
import com.adgomin.manage.mapper.ManageMapper;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service(value = "com.adgomin.manage.service.ManageService")
@Transactional
public class ManageService {
    private final ManageMapper manageMapper;

    private final JoinMapper joinMapper;

    @Autowired
    public ManageService(ManageMapper manageMapper, JoinMapper joinMapper) {
        this.manageMapper = manageMapper;
        this.joinMapper = joinMapper;
    }

    public MediaRegisterEntity[] allMediaRegister(String email) {
        return this.manageMapper.allMediaRegister(email);
    }

    public MediaRegisterVO mediaOrderEmail(String mediaOrder, String email) {
        return this.manageMapper.mediaOrderEmail(mediaOrder, email);
    }

    public MediaRegisterEntity mediaRegisterEntity(String mediaOrder) {
        return this.manageMapper.mediaRegisterEntity(mediaOrder);
    }

    public Enum<?> manageMediaUpdate(MediaRegisterEntity mediaRegisterEntity) {
        return this.manageMapper.manageMediaUpdate(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> manageMediaChangeThumbnail(MultipartFile thumbnail, MediaRegisterEntity mediaRegisterEntity) {
        JoinEntity userOrder = this.joinMapper.userOrder(mediaRegisterEntity.getEmail());
        String path = "../thumbnail/" + userOrder.getUserOrder();

        String originalFilename = thumbnail.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String storeFilename = UUID.randomUUID() + "." + extension;

        File file = new File(path);

        if(!file.exists()) {
            file.mkdirs();
        }

        try {
            Path absolutePath = Paths.get(path  + "/" + storeFilename).toAbsolutePath();
            thumbnail.transferTo(absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRegisterEntity.setThumbnailOriginFileNm(thumbnail.getOriginalFilename());
        mediaRegisterEntity.setThumbnailImgNm(storeFilename);
        mediaRegisterEntity.setThumbnailImgFilePath(path);

        return this.manageMapper.manageMediaChangeThumbnail(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
