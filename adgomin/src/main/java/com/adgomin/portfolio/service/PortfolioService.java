package com.adgomin.portfolio.service;

import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.mapper.JoinMapper;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.mapper.PortfolioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service(value = "com.adgomin.portfolio.service.PortfolioService")
@Transactional
public class PortfolioService {
    private final PortfolioMapper portfolioMapper;

    private final JoinMapper joinMapper;

    @Autowired
    public PortfolioService(PortfolioMapper portfolioMapper, JoinMapper joinMapper) {
        this.portfolioMapper = portfolioMapper;
        this.joinMapper = joinMapper;
    }

    public Enum<?> portfolioRegister(PortfolioEntity portfolioEntity) {
        return this.portfolioMapper.portfolioRegister(portfolioEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> portfolioChangeThumbnail(MultipartFile thumbnail, PortfolioEntity portfolioEntity) {
        JoinEntity userOrder = this.joinMapper.userOrder(portfolioEntity.getEmail());
        String path = "/home/ec2-user/portfolio/thumbnail/" + userOrder.getUserOrder();

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

    public Enum<?> portfolioDetailImg(MultipartFile[] detailImgArr, PortfolioImgEntity portfolioImgEntity) {
        int result = 1;
        JoinEntity userOrder = this.joinMapper.userOrder(portfolioImgEntity.getEmail());
        for(int i = 0; i < detailImgArr.length; i++) {
            String path = "/home/ec2-user/portfolio/details/" + userOrder.getUserOrder();

            String originalFilename = detailImgArr[i].getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String storeFilename = UUID.randomUUID() + "." + extension;

            File file = new File(path);

            if(!file.exists()) {
                file.mkdirs();
            }

            try {
                Path absolutePath = Paths.get(path  + "/" + storeFilename).toAbsolutePath();
                detailImgArr[i].transferTo(absolutePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            portfolioImgEntity.setOriginFileNm(detailImgArr[i].getOriginalFilename());
            portfolioImgEntity.setImgNm(storeFilename);
            portfolioImgEntity.setImgFilePath(path);

            if(this.portfolioMapper.portfolioDetailImg(portfolioImgEntity) == 0) {
                result = 0;
            }
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
