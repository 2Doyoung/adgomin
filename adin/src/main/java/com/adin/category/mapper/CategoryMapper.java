package com.adin.category.mapper;

import com.adin.join.vo.JoinVO;
import com.adin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {
    MediaRegisterVO[] categoryList(@Param(value = "order") String order, @Param(value = "adDetailCategoryKo") String adDetailCategoryKo
    ,@Param(value = "adCategoryKo") String adCategoryKo, @Param(value = "regionKo") String regionKo
    ,@Param(value = "pageStart") int pageStart, @Param(value = "perPageNum") int perPageNum);

    MediaRegisterVO categoryCnt(@Param(value = "order") String order, @Param(value = "adDetailCategoryKo") String adDetailCategoryKo
            ,@Param(value = "adCategoryKo") String adCategoryKo, @Param(value = "regionKo") String regionKo
    );
}
