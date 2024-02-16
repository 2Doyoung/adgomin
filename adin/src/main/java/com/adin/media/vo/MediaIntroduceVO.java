package com.adin.media.vo;

import com.adin.media.entity.MediaIntroduceEntity;

public class MediaIntroduceVO extends MediaIntroduceEntity {
    private String nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
