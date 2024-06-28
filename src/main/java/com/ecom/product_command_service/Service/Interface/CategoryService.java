package com.ecom.product_command_service.Service.Interface;

import com.ecom.product_command_service.Dto.Request.CategoryAddRequest;
import com.ecom.product_command_service.Dto.toDto.CategoryListObject;
import com.ecom.product_command_service.Entity.Category.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    public List<CategoryListObject> addCategories(CategoryAddRequest request);
    public List<CategoryListObject> getAllCategories(UUID storeId);
    public Category getCategoryById(UUID categoryId);




    public void removeCategoryFromProducts( UUID categoryId);
    public void removeCategoryFromProductVariant( UUID categoryId);
    public void removeCategoryFromProductsAndProductVariants( UUID categoryId);

    public Category updateCategory(UUID categoryId, Category categoryDTO);
    public void deleteCategoryById(UUID categoryId);

}
