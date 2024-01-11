package com.adin.admin.service;

import com.adin.admin.mapper.AdminMapper;
import com.adin.enums.CommonResult;
import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.mapper.MediaMapper;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.admin.service.AdminService")
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

    public MediaRegisterEntity submitThumbnailImage(String mediaOrder) {
        return this.adminMapper.submitThumbnailImage(mediaOrder);
    }

    public JoinVO getMediaUserListCnt(JoinVO joinVO) {
        return this.adminMapper.getMediaUserListCnt(joinVO);
    }

    public JoinEntity[] getMediaUserList(int pageStart, int perPageNum, String marketingYn, String certifiedYn, String useYn) {
        return this.adminMapper.getMediaUserList(pageStart, perPageNum, marketingYn, certifiedYn, useYn);
    }

    public JoinVO getAdvertiserUserListCnt() {
        return this.adminMapper.getAdvertiserUserListCnt();
    }

    public JoinEntity[] getAdvertiserUserList(int pageStart, int perPageNum) {
        return this.adminMapper.getAdvertiserUserList(pageStart, perPageNum);
    }
}
