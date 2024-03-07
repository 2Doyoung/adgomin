package com.adin.introduce.mapper;

import com.adin.media.vo.MediaIntroduceVO;
import com.adin.portfolio.entity.PortfolioEntity;
import com.adin.portfolio.entity.PortfolioImgEntity;
import com.adin.portfolio.vo.PortfolioVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IntroduceMapper {
    MediaIntroduceVO getIntroduce(@Param(value = "userOrder") int userOrder);

    PortfolioEntity[] allPortfolio(@Param(value = "email") String email,@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum);

    PortfolioVO getDetailPortfolio(@Param(value = "portfolioOrder") int portfolioOrder);

    PortfolioImgEntity[] getDetailPortfolioImg(@Param(value = "portfolioOrder") int portfolioOrder);
}
