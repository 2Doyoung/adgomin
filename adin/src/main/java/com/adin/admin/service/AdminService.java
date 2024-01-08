package com.adin.admin.service;

import com.adin.admin.mapper.AdminMapper;
import com.adin.media.entity.MediaRegisterEntity;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.admin.service.AdminService")
@Transactional
public class AdminService {
    private final AdminMapper adminMapper;
    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
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
}
