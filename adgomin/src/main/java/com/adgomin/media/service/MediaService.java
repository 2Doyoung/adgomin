package com.adgomin.media.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.mapper.JoinMapper;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.mapper.MediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service(value = "com.adgomin.media.service.MediaService")
@Transactional
public class MediaService {
    private final MediaMapper mediaMapper;

    private final JoinMapper joinMapper;

    @Autowired
    public MediaService(MediaMapper mediaMapper, JoinMapper joinMapper) {
        this.mediaMapper = mediaMapper;
        this.joinMapper = joinMapper;
    }

    public Enum<?> insertMediaEmail(JoinEntity joinEntity) {
        int result = 0;
        int result1 = this.mediaMapper.insertMediaIntroduce(joinEntity);
        int result2 = this.mediaMapper.insertMediaRegister(joinEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> mediaIntroduce(MediaIntroduceEntity mediaIntroduceEntity) {
        return this.mediaMapper.mediaIntroduce(mediaIntroduceEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public MediaIntroduceEntity getMediaIntroduce(String email) {
        return this.mediaMapper.getMediaIntroduce(email);
    }

    public MediaRegisterEntity getMediaRegister(String email) {
        return this.mediaMapper.getMediaRegister(email);
    }

    public Enum<?> mediaRegister(MediaRegisterEntity mediaRegisterEntity) {
        return this.mediaMapper.mediaRegister(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> changeThumbnail(MultipartFile thumbnail, MediaRegisterEntity mediaRegisterEntity) {
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

        return this.mediaMapper.changeThumbnail(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public String mediaDetailExplanationImage(String email, MultipartFile mediaDetailExplanationImage) {
        JoinEntity userOrder = this.joinMapper.userOrder(email);
        String path = "../explanationImage/" + userOrder.getUserOrder();

        String originalFilename = mediaDetailExplanationImage.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String storeFilename = UUID.randomUUID() + "." + extension;

        File file = new File(path);

        if(!file.exists()) {
            file.mkdirs();
        }

        try {
            Path absolutePath = Paths.get(path  + "/" + storeFilename).toAbsolutePath();
            mediaDetailExplanationImage.transferTo(absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path + "/" + storeFilename;
    }

    public MediaRegisterEntity thumbnailImage(String email) {
        return this.mediaMapper.thumbnailImage(email);
    }

    public MediaRegisterEntity getRefuseReason(MediaRegisterEntity mediaRegisterEntity) {
        return this.mediaMapper.getRefuseReason(mediaRegisterEntity);
    }
}
