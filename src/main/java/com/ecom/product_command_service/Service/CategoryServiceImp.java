package com.ecom.product_command_service.Service;

import com.ecom.product_command_service.Dto.Mapper.CategoryMapper;
import com.ecom.product_command_service.Dto.Request.CategoryAddRequest;
import com.ecom.product_command_service.Dto.toDto.CategoryListObject;
import com.ecom.product_command_service.Entity.Category.Category;
import com.ecom.product_command_service.Entity.Products.Product;
import com.ecom.product_command_service.Entity.Products.ProductVariant;
import com.ecom.product_command_service.Exception.Category.CategoryNotFoundException;
import com.ecom.product_command_service.Repository.CategoryRepository;
import com.ecom.product_command_service.Repository.ProductRepository;
import com.ecom.product_command_service.Repository.ProductVariantRepository;
import com.ecom.product_command_service.Service.Interface.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryListObject> addCategories(CategoryAddRequest request) {

        List<Category> categories = request.getCategories().stream().map(
                (category)->
                Category.builder()
                        .storeId(request.getStoreId())
                        .categoryName(category)
                        .build()
        ).collect(Collectors.toList());

        List<CategoryListObject> categoryListObjects = categoryRepository.
                saveAll(categories)
                .stream()
                .map(categoryMapper::EntityToCategoryListObj)
                .collect(Collectors.toList());
        return categoryListObjects;
    }

    @Override
    public List<CategoryListObject> getAllCategories(UUID storeId) {

        List<CategoryListObject> categoryListObjects = categoryRepository.findByStoreId(storeId)
                .stream()
                .filter(Objects::nonNull)
                .map((obj)-> CategoryListObject.builder()
                        .categories_id(obj.getCategoryId())
                        .categoryName(obj.getCategoryName())
                        .build())
                .collect(Collectors.toList());
        return categoryListObjects;
    }

    @Override
    public Category getCategoryById(UUID categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found : "+ categoryId));
    }


    @Override
    public void removeCategoryFromProducts(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//        List<Product> products = productRepository.findAllByCategoriesId(categoryId);
//        for (Product product : products) {
//            product.getCategories().remove(category);
//            productRepository.save(product);
//        }

    }

    public void removeCategoryFromProductVariant(UUID categoryId) {


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//        List<ProductVariant> productVariants = productVariantRepository.findAllByCategoriesId(categoryId);
//        for (ProductVariant variant : productVariants) {
//            variant.getCategories().remove(category);
//            productVariantRepository.save(variant);
//        }
    }

    @Override
    public void removeCategoryFromProductsAndProductVariants( UUID categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//        List<Product> products = productRepository.findAllByCategoriesId(categoryId);
//        for (Product product : products) {
//            product.getCategories().remove(category);
//            productRepository.save(product);
//        }


//        List<ProductVariant> productVariants = productVariantRepository.findAllByCategoriesId(categoryId);
//        for (ProductVariant variant : productVariants) {
//            variant.getCategories().remove(category);
//            productVariantRepository.save(variant);
//        }

    }



    @Override
    public Category updateCategory(UUID categoryId, Category categoryDTO) {
         Category category = categoryRepository.findById(categoryId)
                 .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
         category.setCategoryName(categoryDTO.getCategoryName());
         return categoryRepository.save(category);


    }

    @Override
    public void deleteCategoryById(UUID categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        List<Product> products = productRepository.findAllByCategoriesId(categoryId);
        for (Product product : products) {
            product.getCategories().remove(category);
            productRepository.save(product);
        }


//        List<ProductVariant> productVariants = productVariantRepository.findAllByCategoriesId(categoryId);
//        for (ProductVariant variant : productVariants) {
//            variant.getCategories().remove(category);
//            productVariantRepository.save(variant);
//        }

        // Delete the category
        categoryRepository.delete(category);
    }


}
