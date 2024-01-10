package com.adin.admin.mapper;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    MediaRegisterEntity[] getMediaSubmitList(@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum);

    MediaRegisterVO getMediaSubmitListCnt();

    MediaRegisterVO getMediaSubmitDetail(@Param(value = "mediaOrder") int mediaOrder);

    int judgeComplete(MediaRegisterEntity mediaRegisterEntity);

    MediaRegisterEntity submitThumbnailImage(@Param(value = "mediaOrder") String mediaOrder);

    JoinVO getMediaUserListCnt();

    JoinEntity[] getMediaUserList(@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum);

    JoinVO getAdvertiserUserListCnt();

    JoinEntity[] getAdvertiserUserList(@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum);
}
