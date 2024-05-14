package com.adgomin.admin.service;

import com.adgomin.admin.mapper.AdminMapper;
import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.mapper.MediaMapper;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adgomin.admin.service.AdminService")
@Transactional
public class AdminService {
    private final AdminMapper adminMapper;
    private final MediaMapper mediaMapper;
    @Autowired
    public AdminService(AdminMapper adminMapper, MediaMapper mediaMapper) {
        this.adminMapper = adminMapper;
        this.mediaMapper = mediaMapper;
    }

    public MediaRegisterEntity[] getMediaSubmitList(int pageStart, int perPageNum) {
        return this.adminMapper.getMediaSubmitList(pageStart, perPageNum);
    }

    public MediaRegisterVO getMediaSubmitListCnt() {
        return this.adminMapper.getMediaSubmitListCnt();
    }

    public MediaRegisterVO getMediaSubmitDetail(int mediaOrder) {
        return this.adminMapper.getMediaSubmitDetail(mediaOrder);
    }

    public Enum<?> judgeComplete(MediaRegisterEntity mediaRegisterEntity) {
        JoinEntity joinEntity = new JoinEntity();
        joinEntity.setEmail(mediaRegisterEntity.getEmail());
        int result = 0;
        int result1 = this.mediaMapper.insertMediaRegister(joinEntity);
        int result2 = this.adminMapper.judgeComplete(mediaRegisterEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }
        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> judgeRefuse(MediaRegisterEntity mediaRegisterEntity) {
        return this.adminMapper.judgeRefuse(mediaRegisterEntity) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public MediaRegisterEntity submitThumbnailImage(String mediaOrder) {
        return this.adminMapper.submitThumbnailImage(mediaOrder);
    }

    public JoinVO getMediaUserListCnt(JoinVO joinVO) {
        return this.adminMapper.getMediaUserListCnt(joinVO);
    }

    public JoinEntity[] getMediaUserList(int pageStart, int perPageNum, String marketingYn, String certifiedYn, String useYn) {
        return this.adminMapper.getMediaUserList(pageStart, perPageNum, marketingYn, certifiedYn, useYn);
    }

    public JoinVO getAdvertiserUserListCnt(JoinVO joinVO) {
        return this.adminMapper.getAdvertiserUserListCnt(joinVO);
    }

    public JoinEntity[] getAdvertiserUserList(int pageStart, int perPageNum, String marketingYn, String certifiedYn, String useYn) {
        return this.adminMapper.getAdvertiserUserList(pageStart, perPageNum, marketingYn, certifiedYn, useYn);
    }
}
