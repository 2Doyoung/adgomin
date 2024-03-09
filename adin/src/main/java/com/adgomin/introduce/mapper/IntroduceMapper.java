package com.adgomin.introduce.mapper;

import com.adgomin.media.vo.MediaIntroduceVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IntroduceMapper {
    MediaIntroduceVO getIntroduce(@Param(value = "userOrder") int userOrder);

    PortfolioEntity[] allPortfolio(@Param(value = "email") String email,@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum);

    PortfolioVO getDetailPortfolio(@Param(value = "portfolioOrder") int portfolioOrder);

    PortfolioImgEntity[] getDetailPortfolioImg(@Param(value = "portfolioOrder") int portfolioOrder);
}
