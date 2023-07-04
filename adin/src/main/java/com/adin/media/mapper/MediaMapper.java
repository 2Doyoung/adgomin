package com.adin.media.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.media.entity.MediaIntroduceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.print.attribute.standard.Media;

@Mapper
public interface MediaMapper {
    int insertMediaIntroduce(JoinEntity joinEntity);

    int mediaIntroduce(MediaIntroduceEntity mediaIntroduceEntity);

    MediaIntroduceEntity getMediaIntroduce(@Param(value = "email") String email);
}
