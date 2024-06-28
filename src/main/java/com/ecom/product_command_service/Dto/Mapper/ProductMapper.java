package com.ecom.product_command_service.Dto.Mapper;

import com.ecom.product_command_service.Dto.Request.ProductCreateRequest;
import com.ecom.product_command_service.Dto.Response.ProductResponse;
import com.ecom.product_command_service.Dto.toDto.ProductImageDTO;
import com.ecom.product_command_service.Dto.Response.ProductWithCatagoryResponse;
import com.ecom.product_command_service.Entity.Category.Category;
import com.ecom.product_command_service.Entity.Products.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    private final ProductImageMapper productImageMapper;
    public Product requestToProductsWithCategory(ProductCreateRequest productCreateRequest, List<Category> categories){
        if(productCreateRequest == null){
            return null;
        }
        return Product.builder()
                .storeId(productCreateRequest.getStoreId())
                .productName(productCreateRequest.getProductName())
                .description(productCreateRequest.getDescription())
                .price(productCreateRequest.getPrice())
                .compareAtPrice(productCreateRequest.getCompareAtPrice())
                .quantity(productCreateRequest.getQuantity())
                .archived(productCreateRequest.getArchived())
                .inStock(productCreateRequest.getInStock())
                .published(productCreateRequest.getPublished())
                .productImages(productCreateRequest.getImages()
                        .stream().map(productImageMapper::toEntity).collect(Collectors.toList()))
                .tags(productCreateRequest.getTags())
                .customFields(productCreateRequest.getCustomFields())
                .categories(categories)
                .build();
    }



    public Product requestToProduct(ProductCreateRequest productCreateRequest){
        if(productCreateRequest == null){
            return null;
        }
        return Product.builder()
                .storeId(productCreateRequest.getStoreId())
                .productName(productCreateRequest.getProductName())
                .description(productCreateRequest.getDescription())
                .price(productCreateRequest.getPrice())
                .compareAtPrice(productCreateRequest.getCompareAtPrice())
                .quantity(productCreateRequest.getQuantity())
                .archived(productCreateRequest.getArchived())
                .inStock(productCreateRequest.getInStock())
                .published(productCreateRequest.getPublished())
                .productImages(productCreateRequest.getImages()
                        .stream().map(productImageMapper::toEntity).collect(Collectors.toList()))
                .tags(productCreateRequest.getTags())
                .customFields(productCreateRequest.getCustomFields())
                .build();
    }
    public ProductResponse entityToProduct(Product product){


        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .quantity(product.getQuantity())
                .archived(product.getArchived())
                .inStock(product.getInStock())
                .published(product.getPublished())
                .tags(product.getTags())
                .images(product.getProductImages().stream().map((img)->
                        ProductImageDTO
                        .builder()
                                .productImageId(img.getProductImageId())
                                .imageUrl(img.getImageUrl())
                                .imagePosition(img.getImagePosition())
                                .build()).collect(Collectors.toList()))
                .customFields(product.getCustomFields())
                .build();
    }

    public ProductWithCatagoryResponse entityToProductWithCategory(Product product){

        return ProductWithCatagoryResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .quantity(product.getQuantity())
                .archived(product.getArchived())
                .inStock(product.getInStock())
                .published(product.getPublished())
                .tags(product.getTags())
                .images(product.getProductImages().stream().map((img)->
                        ProductImageDTO
                                .builder()
                                .productImageId(img.getProductImageId())
                                .imageUrl(img.getImageUrl())
                                .imagePosition(img.getImagePosition())
                                .build()).collect(Collectors.toList()))
                .customFields(product.getCustomFields())
                .categories(product.getCategories().stream().map(categoryMapper::EntityToCategoryListObj)
                        .collect(Collectors.toList()))
                .build();
    }


}
