package com.adin.portfolio.entity;

import java.util.Date;

public class PortfolioEntity {
    private int portfolioOrder;
    private String email;
    private String portfolioTitle;
    private String portfolioAdDetailCategory;
    private String portfolioAdCategory;
    private String portfolioRegion;
    private String mainImgNm;
    private String mainOriginFileNm;
    private String mainImgFilePath;
    private Date createDt;
    private Date modifyDt;

    public int getPortfolioOrder() {
        return portfolioOrder;
    }

    public void setPortfolioOrder(int portfolioOrder) {
        this.portfolioOrder = portfolioOrder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortfolioTitle() {
        return portfolioTitle;
    }

    public void setPortfolioTitle(String portfolioTitle) {
        this.portfolioTitle = portfolioTitle;
    }

    public String getPortfolioAdDetailCategory() {
        return portfolioAdDetailCategory;
    }

    public void setPortfolioAdDetailCategory(String portfolioAdDetailCategory) {
        this.portfolioAdDetailCategory = portfolioAdDetailCategory;
    }

    public String getPortfolioAdCategory() {
        return portfolioAdCategory;
    }

    public void setPortfolioAdCategory(String portfolioAdCategory) {
        this.portfolioAdCategory = portfolioAdCategory;
    }

    public String getPortfolioRegion() {
        return portfolioRegion;
    }

    public void setPortfolioRegion(String portfolioRegion) {
        this.portfolioRegion = portfolioRegion;
    }

    public String getMainImgNm() {
        return mainImgNm;
    }

    public void setMainImgNm(String mainImgNm) {
        this.mainImgNm = mainImgNm;
    }

    public String getMainOriginFileNm() {
        return mainOriginFileNm;
    }

    public void setMainOriginFileNm(String mainOriginFileNm) {
        this.mainOriginFileNm = mainOriginFileNm;
    }

    public String getMainImgFilePath() {
        return mainImgFilePath;
    }

    public void setMainImgFilePath(String mainImgFilePath) {
        this.mainImgFilePath = mainImgFilePath;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }
}
