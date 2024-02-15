package com.adin.introduce.service;

import com.adin.introduce.mapper.IntroduceMapper;
import org.springframework.stereotype.Service;

@Service(value = "com.adin.introduce.service.IntroduceService")
public class IntroduceService {
    private final IntroduceMapper introduceMapper;

    public IntroduceService(IntroduceMapper introduceMapper) {
        this.introduceMapper = introduceMapper;
    }
}
