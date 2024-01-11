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

    JoinVO getMediaUserListCnt(JoinVO joinVO);

    JoinEntity[] getMediaUserList(@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum
            , @Param(value = "marketingYn") String marketingYn, @Param(value = "certifiedYn") String certifiedYn, @Param(value = "useYn") String useYn);

    JoinVO getAdvertiserUserListCnt(JoinVO joinVO);

    JoinEntity[] getAdvertiserUserList(@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum
            , @Param(value = "marketingYn") String marketingYn, @Param(value = "certifiedYn") String certifiedYn, @Param(value = "useYn") String useYn);
}
