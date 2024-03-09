package com.adgomin.user.mapper;

import com.adgomin.join.vo.JoinVO;
import com.adgomin.user.entity.SecessionReasonEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int changeProfileImg(JoinVO joinVO);

    JoinVO profileImage(@Param(value = "email") String email);

    int changeNickname(JoinVO joinVO);

    int userPasswordChange(JoinVO joinVO);

    int userSecession(JoinVO joinVO);

    int userSecessionReason(SecessionReasonEntity secessionReasonEntity);
}
