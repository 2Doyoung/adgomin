package com.adgomin.manage.mapper;

import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageMapper {
    MediaRegisterEntity[] allMediaRegister(@Param(value = "email") String email);
}
