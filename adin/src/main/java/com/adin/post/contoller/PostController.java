package com.adin.post.contoller;

import com.adin.media.vo.MediaRegisterVO;
import com.adin.portfolio.entity.PortfolioEntity;
import com.adin.portfolio.vo.PortfolioVO;
import com.adin.post.service.PostService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

@Controller(value = "com.adin.post.controller.PostController")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post")
    public ModelAndView post(@RequestParam(value = "mediaOrder", required = false) String mediaOrder) {
        ModelAndView modelAndView = new ModelAndView("post/post");

        MediaRegisterVO getPost = this.postService.getPost(mediaOrder);

        PortfolioVO getPortfolioCnt = this.postService.getPortfolioCnt(getPost.getEmail());

        if(getPortfolioCnt.getCount() != 0) {
            PortfolioEntity[] getPortfolio = this.postService.getPortfolio(getPost.getEmail());
            modelAndView.addObject("getPortfolio", getPortfolio);
        }

        String mediaPriceReplace = getPost.getMediaPrice().replaceAll(",", "");
        int fiveMonthMediaPrice = Integer.parseInt(mediaPriceReplace) / 5;

        DecimalFormat df = new DecimalFormat("###,###");
        String fiveMonthMediaPriceFormat = df.format(fiveMonthMediaPrice);

        modelAndView.addObject("adDetailCategory", getPost.getAdDetailCategory());
        modelAndView.addObject("mediaTitle", getPost.getMediaTitle());
        modelAndView.addObject("mediaSummary", getPost.getMediaSummary());
        modelAndView.addObject("mediaDetailExplain", getPost.getMediaDetailExplain());
        modelAndView.addObject("thumbnailImgNm", getPost.getThumbnailImgNm());
        modelAndView.addObject("thumbnailOriginFileNm", getPost.getThumbnailOriginFileNm());
        modelAndView.addObject("thumbnailImgFilePath", getPost.getThumbnailImgFilePath());
        modelAndView.addObject("mediaPrice", getPost.getMediaPrice());
        modelAndView.addObject("mediaIntroduce", getPost.getMediaIntroduce());
        modelAndView.addObject("region", getPost.getRegion());
        modelAndView.addObject("adCategory", getPost.getAdCategory());
        modelAndView.addObject("nickname", getPost.getNickname());
        modelAndView.addObject("email", getPost.getEmail());
        modelAndView.addObject("userOrder", getPost.getUserOrder());
        modelAndView.addObject("fiveMonthMediaPriceFormat", fiveMonthMediaPriceFormat);

        return  modelAndView;
    }

    @GetMapping("/portfolio/thumbnail/image")
    public ResponseEntity<Resource> portfolioThumbnailImage(@RequestParam(value = "mainImgNm") String mainImgNm, @RequestParam(value = "mainImgFilePath") String mainImgFilePath) {
        String filePath = null;

        filePath = mainImgFilePath + "/" + mainImgNm;


        Resource resource = new FileSystemResource(filePath);

        HttpHeaders header = new HttpHeaders();
        Path path = null;
        try {
            path = Paths.get(filePath);
            header.add("Content-type", Files.probeContentType(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
}
