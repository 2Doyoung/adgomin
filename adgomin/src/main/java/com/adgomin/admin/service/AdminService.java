package com.adgomin.admin.service;

import com.adgomin.admin.mapper.AdminMapper;
import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.entity.MediaRegisterEntity;
import com.adgomin.media.mapper.MediaMapper;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adgomin.admin.service.AdminService")
@Transactional
public class AdminService {
    @Value("${app.url}")
    private String appUrl;
    private final AdminMapper adminMapper;
    private final MediaMapper mediaMapper;
    @Autowired
    public AdminService(AdminMapper adminMapper, MediaMapper mediaMapper) {
        this.adminMapper = adminMapper;
        this.mediaMapper = mediaMapper;
    }

    public MediaRegisterEntity[] getMediaSubmitList(int pageStart, int perPageNum) {
        return this.adminMapper.getMediaSubmitList(pageStart, perPageNum);
    }

    public MediaRegisterVO getMediaSubmitListCnt() {
        return this.adminMapper.getMediaSubmitListCnt();
    }

    public MediaRegisterVO getMediaSubmitDetail(int mediaOrder) {
        return this.adminMapper.getMediaSubmitDetail(mediaOrder);
    }

    public Enum<?> judgeComplete(MediaRegisterEntity mediaRegisterEntity) {
        JoinEntity joinEntity = new JoinEntity();
        joinEntity.setEmail(mediaRegisterEntity.getEmail());

        String content = "<![CDATA[<h1>광고매체 적합한 광고 🔈</h1>"
                + "<h3> </h3>"
                + "<span>광고매체에 어울리는 광고들을 기재해주세요.</span><h3> </h3>"
                + "<ul>"
                + "<li>병원 광고</li>"
                + "<li>제품 광고</li>"
                + "<li>음식점 광고</li>"
                + "</ul>"
                + "<h3> </h3>"
                + "<s style=\"color: #e9ecef;\">"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
                + "</s>"
                + "<h3> </h3>"
                + "<h1>광고매체 소개 💬</h1>"
                + "<h2>이력 ✅</h2>"
                + "<span>광고매체 카테고리와 관련이 있는 이력을 적어주세요.</span><h3> </h3>"
                + "<span>광고주들은 이력을 보고 광고매체에 신뢰를 가집니다.</span><h3> </h3>"
                + "<ul>"
                + "<li>이력사항 기재</li>"
                + "</ul>"
                + "<h3> </h3>"
                + "<h2>실제 광고 이미지 🌄</h2>"
                + "<span>지금까지 광고를 했던 사진들을 올려주세요.</span><h3> </h3>"
                + "<span>광고주들은 사진을 보고 어떻게 광고가 되는지 확인할 수 있습니다.</span><h3> </h3>"
                + "<span>최대 3개가 적당하며 다른 사진은 포트폴리오에 올리실 수 있습니다.</span><h3> </h3>"
                + "<img src=\"" + appUrl + "/images/media-register-image-guide.png\">"
                + "<h3> </h3>"
                + "<h2>대표 광고 동영상 🎞️</h2>"
                + "<span>광고를 진행했던 대표 동영상 하나를 올려주세요.</span><h3> </h3>"
                + "<span>동영상이 없는 광고매체는 생략을 하셔도 됩니다.</span><h3> </h3>"
                + "<img src=\"" + appUrl + "/images/media-register-video-guide.png\">";

        joinEntity.setMediaDetailExplain(content);

        int result = 0;
        int result1 = this.mediaMapper.insertMediaRegister(joinEntity);
        int result2 = this.adminMapper.judgeComplete(mediaRegisterEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }
        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public Enum<?> judgeRefuse(MediaRegisterEntity mediaRegisterEntity) {
        int result = 0;
        int result1 = this.adminMapper.judgeRefuse(mediaRegisterEntity);
        int result2 = this.adminMapper.judgeRefuseReason(mediaRegisterEntity);

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public MediaRegisterEntity submitThumbnailImage(String mediaOrder) {
        return this.adminMapper.submitThumbnailImage(mediaOrder);
    }

    public JoinVO getMediaUserListCnt(JoinVO joinVO) {
        return this.adminMapper.getMediaUserListCnt(joinVO);
    }

    public JoinEntity[] getMediaUserList(int pageStart, int perPageNum, String marketingYn, String certifiedYn, String useYn) {
        return this.adminMapper.getMediaUserList(pageStart, perPageNum, marketingYn, certifiedYn, useYn);
    }

    public JoinVO getAdvertiserUserListCnt(JoinVO joinVO) {
        return this.adminMapper.getAdvertiserUserListCnt(joinVO);
    }

    public JoinEntity[] getAdvertiserUserList(int pageStart, int perPageNum, String marketingYn, String certifiedYn, String useYn) {
        return this.adminMapper.getAdvertiserUserList(pageStart, perPageNum, marketingYn, certifiedYn, useYn);
    }
}
