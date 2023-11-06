package com.adin.media.service;

import com.adin.enums.CommonResult;
import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.entity.MediaRegisterEntity;
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

    public Enum<?> insertMediaEmail(JoinEntity joinEntity) {
        int result = 0;
        int result1 = this.mediaMapper.insertMediaIntroduce(joinEntity);
        int result2 = this.mediaMapper.insertMediaRegister(joinEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> mediaIntroduce(MediaIntroduceEntity mediaIntroduceEntity) {
        return this.mediaMapper.mediaIntroduce(mediaIntroduceEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public MediaIntroduceEntity getMediaIntroduce(String email) {
        return this.mediaMapper.getMediaIntroduce(email);
    }

    public MediaRegisterEntity getMediaRegister(String email) {
        return this.mediaMapper.getMediaRegister(email);
    }

    public Enum<?> mediaRegister(MediaRegisterEntity mediaRegisterEntity) {
        return this.mediaMapper.mediaRegister(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
