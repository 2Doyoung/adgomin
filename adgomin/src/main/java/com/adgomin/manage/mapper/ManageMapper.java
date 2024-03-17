package com.adgomin.manage.mapper;

import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ManageMapper {
    MediaRegisterEntity[] allMediaRegister(@Param(value = "email") String email);

    MediaRegisterVO mediaOrderEmail(@Param(value = "mediaOrder") String mediaOrder, @Param(value = "email") String email);

    MediaRegisterEntity mediaRegisterEntity(@Param(value = "mediaOrder") String mediaOrder);

    int manageMediaUpdate(MediaRegisterEntity mediaRegisterEntity);

    int manageMediaChangeThumbnail(MediaRegisterEntity mediaRegisterEntity);
}
