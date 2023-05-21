package com.adin.main.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface MainMapper {
    JoinVO setSession(@Param(value = "email") String email);
}
