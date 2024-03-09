package com.adgomin.main.mapper;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MainMapper {
    JoinVO setSession(@Param(value = "email") String email);

    MediaRegisterVO[] popularSnsMedia();
    MediaRegisterVO[] popularTransportMedia();
    MediaRegisterVO[] popularOutdoorMedia();
}
