package com.adgomin.introduce.service;

import com.adgomin.introduce.mapper.IntroduceMapper;
import com.adgomin.media.vo.MediaIntroduceVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adgomin.introduce.service.IntroduceService")
@Transactional
public class IntroduceService {
    private final IntroduceMapper introduceMapper;

    @Autowired
    public IntroduceService(IntroduceMapper introduceMapper) {
        this.introduceMapper = introduceMapper;
    }

    public MediaIntroduceVO getIntroduce(int userOrder) {
        return this.introduceMapper.getIntroduce(userOrder);
    }

    public PortfolioEntity[] allPortfolio(String email, int pageStart, int perPageNum) {
        return this.introduceMapper.allPortfolio(email, pageStart, perPageNum);
    }

    public PortfolioVO getDetailPortfolio(int portfolioOrder) {
        return this.introduceMapper.getDetailPortfolio(portfolioOrder);
    }

    public PortfolioImgEntity[] getDetailPortfolioImg(int portfolioOrder) {
        return this.introduceMapper.getDetailPortfolioImg(portfolioOrder);
    }
}
