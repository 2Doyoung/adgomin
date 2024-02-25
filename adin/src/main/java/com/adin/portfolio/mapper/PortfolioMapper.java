package com.adin.portfolio.mapper;

import com.adin.media.entity.MediaRegisterEntity;
import com.adin.portfolio.entity.PortfolioEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PortfolioMapper {
    int portfolioRegister(PortfolioEntity portfolioEntity);
}
