package com.adin.main.service;

import com.adin.join.entity.JoinEntity;
import com.adin.join.vo.JoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adin.main.mapper.MainMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class MainService {
	
	private final MainMapper mainMapper;
	
	@Autowired
	public MainService(MainMapper mainMapper) {
        this.mainMapper = mainMapper;
    }

	public void setSession(String email, HttpServletRequest request) {
		JoinVO joinVO = this.mainMapper.setSession(email);

		if(joinVO.getCount() > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("LOGIN_USER", joinVO);
		}
	}
}
