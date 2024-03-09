package com.adgomin.portfolio.entity;

import java.util.Date;

public class PortfolioImgEntity {
    private int portfolioImgOrder;
    private int portfolioOrder;
    private String email;
    private String imgNm;
    private String originFileNm;
    private String imgFilePath;
    private Date createDt;
    private Date modifyDt;

    public int getPortfolioImgOrder() {
        return portfolioImgOrder;
    }

    public void setPortfolioImgOrder(int portfolioImgOrder) {
        this.portfolioImgOrder = portfolioImgOrder;
    }

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

    public String getImgNm() {
        return imgNm;
    }

    public void setImgNm(String imgNm) {
        this.imgNm = imgNm;
    }

    public String getOriginFileNm() {
        return originFileNm;
    }

    public void setOriginFileNm(String originFileNm) {
        this.originFileNm = originFileNm;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
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
