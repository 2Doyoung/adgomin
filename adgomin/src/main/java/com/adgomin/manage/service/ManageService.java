package com.adgomin.manage.service;

import com.adgomin.manage.mapper.ManageMapper;
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
}
