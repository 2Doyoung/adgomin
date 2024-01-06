package com.adin.media.entity;

import java.util.Date;

public class MediaRegisterEntity {
    private int mediaOrder;
    private String email;
    private String adDetailCategory;
    private String mediaTitle;
    private String mediaSummary;
    private String mediaDetailExplain;
    private String thumbnailImgNm;
    private String thumbnailOriginFileNm;
    private String thumbnailImgFilePath;
    private String mediaPrice;
    private String mediaSubmitStatus;
    private Date createDt;
    private Date modifyDt;
    private Date submitDt;

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

    public Date getSubmitDt() {
        return submitDt;
    }

    public void setSubmitDt(Date submitDt) {
        this.submitDt = submitDt;
    }

    public String getMediaSubmitStatus() {
        return mediaSubmitStatus;
    }

    public void setMediaSubmitStatus(String mediaSubmitStatus) {
        this.mediaSubmitStatus = mediaSubmitStatus;
    }

    public int getMediaOrder() {
        return mediaOrder;
    }

    public void setMediaOrder(int mediaOrder) {
        this.mediaOrder = mediaOrder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdDetailCategory() {
        return adDetailCategory;
    }

    public void setAdDetailCategory(String adDetailCategory) {
        this.adDetailCategory = adDetailCategory;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaSummary() {
        return mediaSummary;
    }

    public void setMediaSummary(String mediaSummary) {
        this.mediaSummary = mediaSummary;
    }

    public String getMediaDetailExplain() {
        return mediaDetailExplain;
    }

    public void setMediaDetailExplain(String mediaDetailExplain) {
        this.mediaDetailExplain = mediaDetailExplain;
    }

    public String getThumbnailImgNm() {
        return thumbnailImgNm;
    }

    public void setThumbnailImgNm(String thumbnailImgNm) {
        this.thumbnailImgNm = thumbnailImgNm;
    }

    public String getThumbnailOriginFileNm() {
        return thumbnailOriginFileNm;
    }

    public void setThumbnailOriginFileNm(String thumbnailOriginFileNm) {
        this.thumbnailOriginFileNm = thumbnailOriginFileNm;
    }

    public String getThumbnailImgFilePath() {
        return thumbnailImgFilePath;
    }

    public void setThumbnailImgFilePath(String thumbnailImgFilePath) {
        this.thumbnailImgFilePath = thumbnailImgFilePath;
    }

    public String getMediaPrice() {
        return mediaPrice;
    }

    public void setMediaPrice(String mediaPrice) {
        this.mediaPrice = mediaPrice;
    }
}
