package com.adin.main.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface MainMapper {
    JoinVO setSession(@Param(value = "email") String email);

    MediaRegisterVO[] popularSnsMedia();
    MediaRegisterVO[] popularTransportMedia();
    MediaRegisterVO[] popularOutdoorMedia();
}
