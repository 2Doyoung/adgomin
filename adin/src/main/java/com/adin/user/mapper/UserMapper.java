package com.adin.user.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.entity.LoginLogEntity;
import com.adin.join.vo.JoinVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int changeProfileImg(JoinVO joinVO);

    JoinVO profileImage(@Param(value = "email") String email);
}
