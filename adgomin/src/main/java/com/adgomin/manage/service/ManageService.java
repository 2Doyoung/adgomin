package com.adgomin.manage.service;

import com.adgomin.manage.mapper.ManageMapper;
import com.adgomin.media.entity.MediaIntroduceEntity;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adgomin.manage.service.ManageService")
@Transactional
public class ManageService {
    private final ManageMapper manageMapper;

    @Autowired
    public ManageService(ManageMapper manageMapper) {
        this.manageMapper = manageMapper;
    }

    public MediaRegisterEntity[] allMediaRegister(String email) {
        return this.manageMapper.allMediaRegister(email);
    }

    public MediaRegisterVO mediaOrderEmail(String mediaOrder, String email) {
        return this.manageMapper.mediaOrderEmail(mediaOrder, email);
    }
}
