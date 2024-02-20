package com.adin.portfolio.service;

import com.adin.portfolio.mapper.PortfolioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.portfolio.service.PortfolioService")
@Transactional
public class PortfolioService {
    private final PortfolioMapper portfolioMapper;

    @Autowired
    public PortfolioService(PortfolioMapper portfolioMapper) {
        this.portfolioMapper = portfolioMapper;
    }
}
