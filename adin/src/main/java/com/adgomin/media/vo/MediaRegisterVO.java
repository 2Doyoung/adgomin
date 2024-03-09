package com.adgomin.media.vo;

import com.adgomin.media.entity.MediaRegisterEntity;

public class MediaRegisterVO extends MediaRegisterEntity {
    private int cnt;
    private String nickname;
    private String mediaIntroduce;
    private String region;
    private String adCategory;
    private String mediaUrl;
    private String userOrder;

    public String getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(String userOrder) {
        this.userOrder = userOrder;
    }

    public String getMediaIntroduce() {
        return mediaIntroduce;
    }

    public void setMediaIntroduce(String mediaIntroduce) {
        this.mediaIntroduce = mediaIntroduce;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(String adCategory) {
        this.adCategory = adCategory;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
