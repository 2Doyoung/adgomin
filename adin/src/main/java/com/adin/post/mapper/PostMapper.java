package com.adin.post.mapper;

import com.adin.media.vo.MediaRegisterVO;
import com.adin.portfolio.entity.PortfolioEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    MediaRegisterVO getPost(@Param(value = "mediaOrder") String mediaOrder);

    PortfolioEntity[] getPortfolio(@Param(value = "email") String email);
}
