package com.adgomin.post.mapper;

import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    MediaRegisterVO getPost(@Param(value = "mediaOrder") String mediaOrder);

    PortfolioEntity[] getPortfolio(@Param(value = "email") String email);

    PortfolioVO getPortfolioCnt(@Param(value = "email") String email);
}
