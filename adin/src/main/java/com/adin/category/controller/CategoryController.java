package com.adin.category.controller;

import com.adin.category.service.CategoryService;
import com.adin.common.CategoryCriteria;
import com.adin.common.CategoryPaging;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.adin.category.controller.CategoryController")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/category")
    public ModelAndView category(@RequestParam(value = "order", required = false) String order, @RequestParam(value = "adDetailCategory", required = false) String adDetailCategory
            , @RequestParam(value = "adCategory", required = false) String adCategory, @RequestParam(value = "region", required = false) String region, @RequestParam(value = "page", required = false) int page) {
        ModelAndView modelAndView = new ModelAndView("category/category_list");

        String orderKo = "";
        String adDetailCategoryKo = "";
        String adCategoryKo = "";
        String regionKo = "";

        if("".equals(order)) {
            orderKo = "추천순";
        } else if("recommend".equals(order)) {
            orderKo = "추천순";
        } else if("popular".equals(order)) {
            orderKo = "인기순";
        } else if("rating".equals(order)) {
            orderKo = "평점순";
        } else if("recent".equals(order)) {
            orderKo = "최신순";
        } else {
            orderKo = "추천순";
        }

        if("all".equals(adDetailCategory)) {
            adDetailCategoryKo = "전체 카테고리";
        } else if("youtube".equals(adDetailCategory)) {
            adDetailCategoryKo = "유튜브";
        } else if("instagram".equals(adDetailCategory)) {
            adDetailCategoryKo = "인스타그램";
        } else if("facebook".equals(adDetailCategory)) {
            adDetailCategoryKo = "페이스북";
        } else if("instagram".equals(adDetailCategory)) {
            adDetailCategoryKo = "인스타그램";
        } else if("blog".equals(adDetailCategory)) {
            adDetailCategoryKo = "블로그";
        } else if("subway".equals(adDetailCategory)) {
            adDetailCategoryKo = "지하철";
        } else if("bus".equals(adDetailCategory)) {
            adDetailCategoryKo = "버스";
        } else if("building".equals(adDetailCategory)) {
            adDetailCategoryKo = "옥외광고";
        } else if("movie".equals(adDetailCategory)) {
            adDetailCategoryKo = "영화관";
        } else if("flag".equals(adDetailCategory)) {
            adDetailCategoryKo = "현수막";
        } else {
            modelAndView = new ModelAndView("error/error");
        }

        if("game".equals(adCategory)) {
            adCategoryKo = "게임";
        } else if("mukbang".equals(adCategory)) {
            adCategoryKo = "먹방";
        } else if("app".equals(adCategory)) {
            adCategoryKo = "어플";
        } else if("hospital".equals(adCategory)) {
            adCategoryKo = "병원";
        } else if("restaurant".equals(adCategory)) {
            adCategoryKo = "음식점";
        } else if("cosmetics".equals(adCategory)) {
            adCategoryKo = "화장품";
        } else if("academy".equals(adCategory)) {
            adCategoryKo = "학원";
        } else if("food".equals(adCategory)) {
            adCategoryKo = "식품";
        } else if("product".equals(adCategory)) {
            adCategoryKo = "제품";
        }

        if("Seoul".equals(region)) {
            regionKo = "서울";
        } else if("Incheon".equals(region)) {
            regionKo = "인천";
        } else if("Busan".equals(region)) {
            regionKo = "부산";
        } else if("Daejeon".equals(region)) {
            regionKo = "대전";
        } else if("Gwangju".equals(region)) {
            regionKo = "광주";
        } else if("Daegu".equals(region)) {
            regionKo = "대구";
        } else if("Ulsan".equals(region)) {
            regionKo = "울산";
        } else if("Gyeonggi".equals(region)) {
            regionKo = "경기";
        } else if("Gangwon".equals(region)) {
            regionKo = "강원";
        } else if("Chungbuk".equals(region)) {
            regionKo = "충북";
        } else if("Chungnam".equals(region)) {
            regionKo = "충남";
        } else if("Jeonbuk".equals(region)) {
            regionKo = "전북";
        } else if("Jeonnam".equals(region)) {
            regionKo = "전남";
        } else if("Gyeongbuk".equals(region)) {
            regionKo = "경북";
        } else if("Gyeongnam".equals(region)) {
            regionKo = "경남";
        } else if("Jeju".equals(region)) {
            regionKo = "제주";
        } else if("Sejong".equals(region)) {
            regionKo = "세종";
        }

        MediaRegisterVO categoryCnt = this.categoryService.categoryCnt(order, adDetailCategoryKo, adCategoryKo, regionKo);

        int cnt = categoryCnt.getCnt();
        CategoryCriteria cri = new CategoryCriteria();
        cri.setPage(page);

        CategoryPaging paging = new CategoryPaging();
        paging.setCri(cri);
        paging.setTotalCount(cnt);

        int pageStart = cri.getPageStart();
        int perPageNum = cri.getPerPageNum();

        MediaRegisterVO[] categoryList = this.categoryService.categoryList(order, adDetailCategoryKo, adCategoryKo, regionKo, pageStart, perPageNum);

        modelAndView.addObject("orderKo", orderKo);
        modelAndView.addObject("adDetailCategoryKo", adDetailCategoryKo);
        modelAndView.addObject("adCategoryKo", adCategoryKo);
        modelAndView.addObject("regionKo", regionKo);

        modelAndView.addObject("order", order);
        modelAndView.addObject("adDetailCategory", adDetailCategory);
        modelAndView.addObject("adCategory", adCategory);
        modelAndView.addObject("region", region);

        modelAndView.addObject("categoryList", categoryList);

        modelAndView.addObject("paging", paging);

        return  modelAndView;
    }
}
