package com.adin.introduce.service;

import com.adin.introduce.mapper.IntroduceMapper;
import com.adin.media.vo.MediaIntroduceVO;
import org.springframework.stereotype.Service;

@Service(value = "com.adin.introduce.service.IntroduceService")
public class IntroduceService {
    private final IntroduceMapper introduceMapper;

    public IntroduceService(IntroduceMapper introduceMapper) {
        this.introduceMapper = introduceMapper;
    }

    public MediaIntroduceVO getIntroduce(int userOrder) {
        return this.introduceMapper.getIntroduce(userOrder);
    }
}
