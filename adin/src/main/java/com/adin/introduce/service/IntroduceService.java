package com.adin.introduce.service;

import com.adin.introduce.mapper.IntroduceMapper;
import com.adin.media.vo.MediaIntroduceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.introduce.service.IntroduceService")
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
}
