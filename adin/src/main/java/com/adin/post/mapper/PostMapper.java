package com.adin.post.mapper;

import com.adin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    MediaRegisterVO getPost(@Param(value = "mediaOrder") String mediaOrder);
}
