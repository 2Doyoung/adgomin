package com.adgomin.post.service;

import com.adgomin.media.vo.MediaRegisterVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import com.adgomin.post.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adgomin.post.service.PostService")
@Transactional
public class PostService {
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public MediaRegisterVO getPost(String mediaOrder) {
        return this.postMapper.getPost(mediaOrder);
    }

    public PortfolioEntity[] getPortfolio(String email) {
        return this.postMapper.getPortfolio(email);
    }

    public PortfolioVO getPortfolioCnt(String email) {
        return this.postMapper.getPortfolioCnt(email);
    }
}
