package com.adin.media.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaIntroduceEntity;
import com.adin.media.entity.MediaRegisterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.print.attribute.standard.Media;

@Mapper
public interface MediaMapper {
    int insertMediaIntroduce(JoinEntity joinEntity);

    int insertMediaRegister(JoinEntity joinEntity);

    int mediaIntroduce(MediaIntroduceEntity mediaIntroduceEntity);

    MediaIntroduceEntity getMediaIntroduce(@Param(value = "email") String email);

    MediaRegisterEntity getMediaRegister(@Param(value = "email") String email);

    int mediaRegister(MediaRegisterEntity mediaRegisterEntity);

    int changeThumbnail(MediaRegisterEntity mediaRegisterEntity);

    MediaRegisterEntity thumbnailImage(@Param(value = "email") String email);
}
