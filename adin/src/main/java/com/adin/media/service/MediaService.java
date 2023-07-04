package com.adin.media.service;

import com.adin.enums.CommonResult;
import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.mapper.MediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.media.service.MediaService")
@Transactional
public class MediaService {
    private final MediaMapper mediaMapper;

    @Autowired
    public MediaService(MediaMapper mediaMapper) {
        this.mediaMapper = mediaMapper;
    }

    public Enum<?> insertMediaIntroduce(JoinEntity joinEntity) {
        return this.mediaMapper.insertMediaIntroduce(joinEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> mediaIntroduce(MediaIntroduceEntity mediaIntroduceEntity) {
        return this.mediaMapper.mediaIntroduce(mediaIntroduceEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public MediaIntroduceEntity getMediaIntroduce(String email) {
        return this.mediaMapper.getMediaIntroduce(email);
    }
}
