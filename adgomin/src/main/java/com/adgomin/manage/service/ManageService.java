package com.adgomin.manage.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.mapper.JoinMapper;
import com.adgomin.manage.mapper.ManageMapper;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
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

    public Enum<?> manageMediaDeleteAdd(MediaRegisterEntity mediaRegisterEntity) {
        int result = 0;
        int result1 = this.manageMapper.manageMediaDelete(mediaRegisterEntity);
        int result2 = this.manageMapper.manageMediaAdd(mediaRegisterEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> manageMediaDelete(MediaRegisterEntity mediaRegisterEntity) {
        return this.manageMapper.manageMediaDelete(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public PortfolioEntity[] allPortfolio(String email) {
        return this.manageMapper.allPortfolio(email);
    }

    public PortfolioVO portfolioOrderEmail(String portfolioOrder, String email) {
        return this.manageMapper.portfolioOrderEmail(portfolioOrder, email);
    }

    public PortfolioEntity portfolioEntity(String portfolioOrder) {
        return this.manageMapper.portfolioEntity(portfolioOrder);
    }

    public PortfolioImgEntity[] portfolioImgEntity(String portfolioOrder) {
        return this.manageMapper.portfolioImgEntity(portfolioOrder);
    }
}
