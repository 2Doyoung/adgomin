package com.adgomin.manage.mapper;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageMapper {
    MediaRegisterEntity[] allMediaRegister(@Param(value = "email") String email);

    MediaRegisterVO mediaOrderEmail(@Param(value = "mediaOrder") String mediaOrder, @Param(value = "email") String email);

    MediaRegisterEntity mediaRegisterEntity(@Param(value = "mediaOrder") String mediaOrder);

    int manageMediaUpdate(MediaRegisterEntity mediaRegisterEntity);

    int manageMediaChangeThumbnail(MediaRegisterEntity mediaRegisterEntity);

    int manageMediaDelete(MediaRegisterEntity mediaRegisterEntity);

    int manageMediaAdd(MediaRegisterEntity mediaRegisterEntity);

    PortfolioEntity[] allPortfolio(@Param(value = "email") String email);

    PortfolioVO portfolioOrderEmail(@Param(value = "portfolioOrder") String portfolioOrder, @Param(value = "email") String email);

    PortfolioEntity portfolioEntity(@Param(value = "portfolioOrder") String portfolioOrder);

    PortfolioImgEntity[] portfolioImgEntity(@Param(value = "portfolioOrder") String portfolioOrder);

    int managePortfolioUpdate(PortfolioEntity portfolioEntity);

    int managePortfolioChangeThumbnail(PortfolioEntity portfolioEntity);

    int managePortfolioDetailImgDelete(PortfolioImgEntity portfolioImgEntity);

    int managePortfolioDetailImgUpdate(PortfolioImgEntity portfolioImgEntity);

    int managePortfolioDelete(PortfolioEntity portfolioEntity);
}
