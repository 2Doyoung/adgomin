package com.adin.join.entity;

import java.util.Date;
import java.util.Objects;

public class JoinEntity {
    private String email;
    private String password;
    private String nickname;
    private String userType;
    private String marketingYn;
    private String useYn;
    private Date createDt;
    private Date modifyDt;

    private String certified;
    private String certifiedYn;

    private String profileImgNm;
    private String profileOriginFileNm;
    private String profileImgFilePath;

    public String getProfileImgNm() {
        return profileImgNm;
    }

    public void setProfileImgNm(String profileImgNm) {
        this.profileImgNm = profileImgNm;
    }

    public String getProfileOriginFileNm() {
        return profileOriginFileNm;
    }

    public void setProfileOriginFileNm(String profileOriginFileNm) {
        this.profileOriginFileNm = profileOriginFileNm;
    }

    public String getProfileImgFilePath() {
        return profileImgFilePath;
    }

    public void setProfileImgFilePath(String profileImgFilePath) {
        this.profileImgFilePath = profileImgFilePath;
    }

    public String getCertifiedYn() {
        return certifiedYn;
    }

    public void setCertifiedYn(String certifiedYn) {
        this.certifiedYn = certifiedYn;
    }

    public String getCertified() {
        return certified;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMarketingYn() {
        return marketingYn;
    }

    public void setMarketingYn(String marketingYn) {
        this.marketingYn = marketingYn;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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
