package com.adgomin.media.mapper;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    MediaRegisterVO getRefuseCount(@Param(value = "userOrder") int userOrder);

    MediaRegisterEntity getRefuseMediaOrder(@Param(value = "userOrder") int userOrder);
}
