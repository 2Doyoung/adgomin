package com.adin.email.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailMapper {
    String selectCertified(@Param(value = "email") String email);
}
