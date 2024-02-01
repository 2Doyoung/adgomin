package com.adin.post.contoller;

import com.adin.common.CategoryCriteria;
import com.adin.common.CategoryPaging;
import com.adin.media.vo.MediaRegisterVO;
import com.adin.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.post.controller.PostController")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/post")
    public ModelAndView post() {
        ModelAndView modelAndView = new ModelAndView("post/post");

        return  modelAndView;
    }
}
