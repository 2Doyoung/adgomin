package com.adin.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adin.main.mapper.MainMapper;

@Service
public class MainService {
	
	private final MainMapper mainMapper;
	
	@Autowired
	public MainService(MainMapper mainMapper) {
        this.mainMapper = mainMapper;
    }
}
