package com.ecom.product_command_service.Dto.Mapper;

import com.ecom.product_command_service.Dto.toDto.CategoryListObject;
import com.ecom.product_command_service.Entity.Category.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryListObject EntityToCategoryListObj(Category category){
        return CategoryListObject
                .builder()
                .categories_id(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
