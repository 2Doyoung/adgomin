package com.adgomin.join.mapper;

import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.entity.LoginLogEntity;
import com.adgomin.join.vo.JoinVO;
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
