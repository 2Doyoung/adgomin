package com.adin.post.service;

import com.adin.media.vo.MediaRegisterVO;
import com.adin.portfolio.entity.PortfolioEntity;
import com.adin.portfolio.vo.PortfolioVO;
import com.adin.post.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.post.service.PostService")
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
