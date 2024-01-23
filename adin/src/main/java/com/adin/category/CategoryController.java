package com.adin.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.category.controller.CategoryController")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category")
    public ModelAndView getJoin(@RequestParam(value = "media", required = false) String media, @RequestParam(value = "adCategory", required = false) String adCategory, @RequestParam(value = "region", required = false) String region) {
        ModelAndView modelAndView = new ModelAndView("category/category_list");

        String mediaKo = "";
        if("all".equals(media)) {
            mediaKo = "전체 카테고리";
        } else if("youtube".equals(media)) {
            mediaKo = "유튜브";
        } else if("instagram".equals(media)) {
            mediaKo = "인스타그램";
        } else if("facebook".equals(media)) {
            mediaKo = "페이스북";
        } else if("instagram".equals(media)) {
            mediaKo = "인스타그램";
        } else if("blog".equals(media)) {
            mediaKo = "블로그";
        } else if("subway".equals(media)) {
            mediaKo = "지하철";
        } else if("bus".equals(media)) {
            mediaKo = "버스";
        } else if("building".equals(media)) {
            mediaKo = "옥외광고";
        } else if("movie".equals(media)) {
            mediaKo = "영화관";
        } else if("flag".equals(media)) {
            mediaKo = "현수막";
        }

        modelAndView.addObject("mediaKo", mediaKo);
        modelAndView.addObject("media", media);
        modelAndView.addObject("adCategory", adCategory);
        modelAndView.addObject("region", region);

        return  modelAndView;
    }
}
