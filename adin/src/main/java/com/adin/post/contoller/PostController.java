package com.adin.post.contoller;

import com.adin.media.vo.MediaRegisterVO;
import com.adin.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        modelAndView.addObject("fiveMonthMediaPriceFormat", fiveMonthMediaPriceFormat);

        return  modelAndView;
    }
}
