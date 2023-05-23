package com.adin.join.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.entity.LoginLogEntity;
import com.adin.join.vo.JoinVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JoinMapper {
    int join(JoinEntity joinEntity);

    JoinVO emailCheck(@Param(value = "email") String email);

    JoinVO loginUser(JoinEntity joinEntity);

    JoinVO certifiedCheck(@Param(value = "email") String email, @Param(value = "certified") String certified);

    int certifiedUpdate(@Param(value = "email") String email);

    int insertLoginLog(LoginLogEntity loginLogEntity);

    int passwordChange(JoinEntity joinEntity);
}
