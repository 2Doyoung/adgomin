package com.adgomin.media.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.mapper.JoinMapper;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.mapper.MediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${app.url}")
    private String appUrl;
    private final MediaMapper mediaMapper;

    private final JoinMapper joinMapper;

    @Autowired
    public MediaService(MediaMapper mediaMapper, JoinMapper joinMapper) {
        this.mediaMapper = mediaMapper;
        this.joinMapper = joinMapper;
    }

    public Enum<?> insertMediaEmail(JoinEntity joinEntity) {
        String content = "<![CDATA[<h1>광고매체 적합한 광고 🔈</h1>"
                + "<h3> </h3>"
                + "<span>광고매체에 어울리는 광고들을 기재해주세요.</span><h3> </h3>"
                + "<ul>"
                + "<li>병원 광고</li>"
                + "<li>제품 광고</li>"
                + "<li>음식점 광고</li>"
                + "</ul>"
                + "<h3> </h3>"
                + "<s style=\"color: #e9ecef;\">"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "</s>"
                + "<h3> </h3>"
                + "<h1>광고매체 소개 💬</h1>"
                + "<h2>이력 ✅</h2>"
                + "<span>광고매체 카테고리와 관련이 있는 이력을 적어주세요.</span><h3> </h3>"
                + "<span>광고주들은 이력을 보고 광고매체에 신뢰를 가집니다.</span><h3> </h3>"
                + "<ul>"
                + "<li>이력사항 기재</li>"
                + "</ul>"
                + "<h3> </h3>"
                + "<h2>실제 광고 이미지 🌄</h2>"
                + "<span>지금까지 광고를 했던 사진들을 올려주세요.</span><h3> </h3>"
                + "<span>광고주들은 사진을 보고 어떻게 광고가 되는지 확인할 수 있습니다.</span><h3> </h3>"
                + "<span>최대 3개가 적당하며 다른 사진은 포트폴리오에 올리실 수 있습니다.</span><h3> </h3>"
                + "<img src=\"" + appUrl + "/images/media-register-image-guide.png\">"
                + "<h3> </h3>"
                + "<h2>대표 광고 동영상 🎞️</h2>"
                + "<span>광고를 진행했던 대표 동영상 하나를 올려주세요.</span><h3> </h3>"
                + "<span>동영상이 없는 광고매체는 생략을 하셔도 됩니다.</span><h3> </h3>"
                + "<img src=\"" + appUrl + "/images/media-register-video-guide.png\">";

        joinEntity.setMediaDetailExplain(content);

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
        String path = "/home/ec2-user/thumbnail/" + userOrder.getUserOrder();

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
        String path = "/home/ec2-user/explanationImage/" + userOrder.getUserOrder();

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

    public Enum<?> mediaNotificationRead(MediaRegisterEntity mediaRegisterEntity) {
        return this.mediaMapper.mediaNotificationRead(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
