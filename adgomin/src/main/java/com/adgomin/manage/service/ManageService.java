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

    public Enum<?> managePortfolioUpdate(PortfolioEntity portfolioEntity) {
        return this.manageMapper.managePortfolioUpdate(portfolioEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> managePortfolioChangeThumbnail(MultipartFile thumbnail, PortfolioEntity portfolioEntity) {
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

        return this.manageMapper.managePortfolioChangeThumbnail(portfolioEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> managePortfolioDetailImgUpdate(PortfolioImgEntity[] portfolioImgEntities, MultipartFile[] detailImgArr, PortfolioImgEntity portfolioImgEntity) {
        int result = 0;

        int result1 = this.manageMapper.managePortfolioDetailImgDelete(portfolioImgEntity);
        int result2 = 1;

        if(portfolioImgEntities.length != 0) {
            for(int i = 0; i < portfolioImgEntities.length; i++) {
                if(this.manageMapper.managePortfolioDetailImgUpdate(portfolioImgEntities[i]) == 0) {
                    result2 = 0;
                }
            }
        }

        int result3 = 1;

        if(detailImgArr != null) {
            JoinEntity userOrder = this.joinMapper.userOrder(portfolioImgEntity.getEmail());
            for(int i = 0; i < detailImgArr.length; i++) {
                String path = "/home/ec2-user/portfolio/details/" + userOrder.getUserOrder();

                String originalFilename = detailImgArr[i].getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                String storeFilename = UUID.randomUUID() + "." + extension;

                File file = new File(path);

                if (!file.exists()) {
                    file.mkdirs();
                }

                try {
                    Path absolutePath = Paths.get(path + "/" + storeFilename).toAbsolutePath();
                    detailImgArr[i].transferTo(absolutePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                portfolioImgEntity.setOriginFileNm(detailImgArr[i].getOriginalFilename());
                portfolioImgEntity.setImgNm(storeFilename);
                portfolioImgEntity.setImgFilePath(path);

                if (this.manageMapper.managePortfolioDetailImgUpdate(portfolioImgEntity) == 0) {
                    result3 = 0;
                }
            }
        }

        if(result1 > 0 && result2 > 0 && result3 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> managePortfolioDelete(PortfolioEntity portfolioEntity) {
        PortfolioImgEntity portfolioImgEntity = new PortfolioImgEntity();

        portfolioImgEntity.setPortfolioOrder(portfolioEntity.getPortfolioOrder());

        int result = 0;
        int result1 = this.manageMapper.managePortfolioDetailImgDelete(portfolioImgEntity);
        int result2 = this.manageMapper.managePortfolioDelete(portfolioEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
