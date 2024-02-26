package com.adin.portfolio.service;

import com.adin.enums.CommonResult;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.portfolio.entity.PortfolioEntity;
import com.adin.portfolio.mapper.PortfolioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service(value = "com.adin.portfolio.service.PortfolioService")
@Transactional
public class PortfolioService {
    private final PortfolioMapper portfolioMapper;

    @Autowired
    public PortfolioService(PortfolioMapper portfolioMapper) {
        this.portfolioMapper = portfolioMapper;
    }

    public Enum<?> portfolioRegister(PortfolioEntity portfolioEntity) {
        return this.portfolioMapper.portfolioRegister(portfolioEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> portfolioChangeThumbnail(MultipartFile thumbnail, PortfolioEntity portfolioEntity) {
        String path = "../portfolio/thumbnail/" + portfolioEntity.getEmail();

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
        portfolioEntity.setMainOriginFileNm(thumbnail.getOriginalFilename());
        portfolioEntity.setMainImgNm(storeFilename);
        portfolioEntity.setMainImgFilePath(path);

        return this.portfolioMapper.portfolioChangeThumbnail(portfolioEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
