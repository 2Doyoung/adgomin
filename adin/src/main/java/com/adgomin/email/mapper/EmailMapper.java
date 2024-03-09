package com.adgomin.email.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailMapper {
    String selectCertified(@Param(value = "email") String email);
}
