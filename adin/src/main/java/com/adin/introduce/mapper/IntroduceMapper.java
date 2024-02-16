package com.adin.introduce.mapper;

import com.adin.media.vo.MediaIntroduceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IntroduceMapper {
    MediaIntroduceVO getIntroduce(@Param(value = "userOrder") int userOrder);
}
