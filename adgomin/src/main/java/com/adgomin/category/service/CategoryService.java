package com.adgomin.category.service;

import com.adgomin.category.mapper.CategoryMapper;
import com.adgomin.media.vo.MediaRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adgomin.category.service.CategoryService")
@Transactional
public class CategoryService {
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public MediaRegisterVO[] categoryList(String order, String adDetailCategoryKo, String adCategoryKo, String regionKo, int pageStart, int perPageNum) {
        if("전체 카테고리".equals(adDetailCategoryKo)) {
            adDetailCategoryKo = "";
        }
        return this.categoryMapper.categoryList(order, adDetailCategoryKo, adCategoryKo, regionKo, pageStart, perPageNum);
    }

    public MediaRegisterVO categoryCnt(String order, String adDetailCategoryKo, String adCategoryKo, String regionKo) {
        if("전체 카테고리".equals(adDetailCategoryKo)) {
            adDetailCategoryKo = "";
        }

        return this.categoryMapper.categoryCnt(order, adDetailCategoryKo, adCategoryKo, regionKo);
    }

    public MediaRegisterVO searchCnt(String keyword) {
        return this.categoryMapper.searchCnt(keyword);
    }

    public MediaRegisterVO[] searchList(String keyword, int pageStart, int perPageNum) {
        return this.categoryMapper.searchList(keyword, pageStart, perPageNum);
    }
}
