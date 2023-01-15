package com.adin.join.service;

import com.adin.join.mapper.JoinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "com.adin.join.service.JoinService")
public class JoinService {
    private final JoinMapper joinMapper;

    @Autowired
    public JoinService(JoinMapper joinMapper) {
        this.joinMapper = joinMapper;
    }
}
