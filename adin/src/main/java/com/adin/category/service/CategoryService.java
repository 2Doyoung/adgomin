package com.adin.category.service;

import com.adin.category.mapper.CategoryMapper;
import com.adin.join.vo.JoinVO;
import com.adin.media.vo.MediaRegisterVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "com.adin.category.service.CategoryService")
@Transactional
public class CategoryService {
    private final CategoryMapper categoryMapper;

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
}
