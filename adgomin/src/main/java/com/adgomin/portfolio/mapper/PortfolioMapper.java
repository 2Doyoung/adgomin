package com.adgomin.portfolio.mapper;

import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PortfolioMapper {
    int portfolioRegister(PortfolioEntity portfolioEntity);

    int portfolioChangeThumbnail(PortfolioEntity portfolioEntity);

    int portfolioDetailImg(PortfolioImgEntity portfolioImgEntity);
}
