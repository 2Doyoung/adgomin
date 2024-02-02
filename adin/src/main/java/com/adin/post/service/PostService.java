package com.adin.post.service;

import com.adin.media.vo.MediaRegisterVO;
import com.adin.post.mapper.PostMapper;
import org.springframework.stereotype.Service;

@Service(value = "com.adin.post.service.PostService")
public class PostService {
    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public MediaRegisterVO getPost(String mediaOrder) {
        return this.postMapper.getPost(mediaOrder);
    }
}
