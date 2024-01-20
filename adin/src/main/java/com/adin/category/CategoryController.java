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

        return  modelAndView;
    }
}
