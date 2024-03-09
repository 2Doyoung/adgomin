package com.adgomin.introduce.controller;

import com.adgomin.common.AllPortfolioCriteria;
import com.adgomin.common.AllPortfolioPaging;
import com.adgomin.introduce.service.IntroduceService;
import com.adgomin.media.vo.MediaIntroduceVO;
import com.adgomin.portfolio.entity.PortfolioEntity;
import com.adgomin.portfolio.entity.PortfolioImgEntity;
import com.adgomin.portfolio.vo.PortfolioVO;
import com.adgomin.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adgomin.introduce.controller.IntroduceController")
public class IntroduceController {
    private final IntroduceService introduceService;
    private final PostService postService;

    @Autowired
    public IntroduceController(IntroduceService introduceService, PostService postService) {
        this.introduceService = introduceService;
        this.postService = postService;
    }

    @GetMapping(value = "/introduce/{userOrder}")
    public ModelAndView introduce(@PathVariable("userOrder") int userOrder) {
        ModelAndView modelAndView = new ModelAndView("introduce/introduce");

        MediaIntroduceVO getIntroduce = this.introduceService.getIntroduce(userOrder);

        PortfolioVO getPortfolioCnt = this.postService.getPortfolioCnt(getIntroduce.getEmail());

        if(getPortfolioCnt.getCount() != 0) {
            PortfolioEntity[] getPortfolio = this.postService.getPortfolio(getIntroduce.getEmail());
            modelAndView.addObject("getPortfolio", getPortfolio);
        }

        modelAndView.addObject("email", getIntroduce.getEmail());
        modelAndView.addObject("mediaIntroduce", getIntroduce.getMediaIntroduce());
        modelAndView.addObject("region", getIntroduce.getRegion());
        modelAndView.addObject("adCategory", getIntroduce.getAdCategory());
        modelAndView.addObject("mediaUrl", getIntroduce.getMediaUrl());
        modelAndView.addObject("nickname", getIntroduce.getNickname());
        modelAndView.addObject("profileImgNm", getIntroduce.getProfileImgNm());
        modelAndView.addObject("profileOriginFileNm", getIntroduce.getProfileOriginFileNm());
        modelAndView.addObject("profileImgFilePath", getIntroduce.getProfileImgFilePath());

        return  modelAndView;
    }

    @GetMapping(value = "/introduce/all/portfolio/{userOrder}")
    public ModelAndView introduceAllPortfolio(@PathVariable("userOrder") int userOrder, @RequestParam(value = "page", required = false) int page) {
        ModelAndView modelAndView = new ModelAndView("introduce/all_portfolio");

        MediaIntroduceVO getIntroduce = this.introduceService.getIntroduce(userOrder);

        PortfolioVO getPortfolioCnt = this.postService.getPortfolioCnt(getIntroduce.getEmail());

        int cnt = getPortfolioCnt.getCount();
        AllPortfolioCriteria cri = new AllPortfolioCriteria();
        cri.setPage(page);

        AllPortfolioPaging paging = new AllPortfolioPaging();
        paging.setCri(cri);
        paging.setTotalCount(cnt);

        int pageStart = cri.getPageStart();
        int perPageNum = cri.getPerPageNum();

        PortfolioEntity[] getPortfolio = this.introduceService.allPortfolio(getIntroduce.getEmail(), pageStart, perPageNum);
        modelAndView.addObject("getPortfolio", getPortfolio);

        modelAndView.addObject("email", getIntroduce.getEmail());
        modelAndView.addObject("nickname", getIntroduce.getNickname());
        modelAndView.addObject("profileImgNm", getIntroduce.getProfileImgNm());
        modelAndView.addObject("profileOriginFileNm", getIntroduce.getProfileOriginFileNm());
        modelAndView.addObject("profileImgFilePath", getIntroduce.getProfileImgFilePath());

        modelAndView.addObject("paging", paging);

        return  modelAndView;
    }

    @GetMapping(value = "/introduce/detail/portfolio/{userOrder}/{portfolioOrder}")
    public ModelAndView introduceDetailPortfolio(@PathVariable("userOrder") int userOrder, @PathVariable("portfolioOrder") int portfolioOrder) {
        ModelAndView modelAndView = new ModelAndView("introduce/detail_portfolio");

        MediaIntroduceVO getIntroduce = this.introduceService.getIntroduce(userOrder);
        PortfolioVO getDetailPortfolio = this.introduceService.getDetailPortfolio(portfolioOrder);
        PortfolioImgEntity[] getDetailPortfolioImg = this.introduceService.getDetailPortfolioImg(portfolioOrder);

        PortfolioEntity[] getPortfolio = this.postService.getPortfolio(getIntroduce.getEmail());

        modelAndView.addObject("getPortfolio", getPortfolio);

        modelAndView.addObject("email", getIntroduce.getEmail());
        modelAndView.addObject("nickname", getIntroduce.getNickname());
        modelAndView.addObject("profileImgNm", getIntroduce.getProfileImgNm());
        modelAndView.addObject("profileOriginFileNm", getIntroduce.getProfileOriginFileNm());
        modelAndView.addObject("profileImgFilePath", getIntroduce.getProfileImgFilePath());

        modelAndView.addObject("portfolioOrder", getDetailPortfolio.getPortfolioOrder());
        modelAndView.addObject("email", getDetailPortfolio.getEmail());
        modelAndView.addObject("portfolioTitle", getDetailPortfolio.getPortfolioTitle());
        modelAndView.addObject("portfolioAdDetailCategory", getDetailPortfolio.getPortfolioAdDetailCategory());
        modelAndView.addObject("portfolioAdCategory", getDetailPortfolio.getPortfolioAdCategory());
        modelAndView.addObject("portfolioRegion", getDetailPortfolio.getPortfolioRegion());
        modelAndView.addObject("mainImgNm", getDetailPortfolio.getMainImgNm());
        modelAndView.addObject("mainOriginFileNm", getDetailPortfolio.getMainOriginFileNm());
        modelAndView.addObject("mainImgFilePath", getDetailPortfolio.getMainImgFilePath());

        modelAndView.addObject("getDetailPortfolioImg", getDetailPortfolioImg);

        return  modelAndView;
    }
}