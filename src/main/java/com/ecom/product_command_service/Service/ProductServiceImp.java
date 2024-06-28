package com.ecom.product_command_service.Service;

import com.ecom.product_command_service.Dto.Mapper.ProductMapper;
import com.ecom.product_command_service.Dto.OperationDtos.DeleteCategoryProduct;
import com.ecom.product_command_service.Dto.OperationDtos.DeleteProduct;
import com.ecom.product_command_service.Dto.OperationDtos.WriteListCategoryProduct;
import com.ecom.product_command_service.Dto.Request.ProductCreateRequest;
import com.ecom.product_command_service.Dto.Response.ProductResponse;
import com.ecom.product_command_service.Dto.Response.ProductWithCatagoryResponse;
import com.ecom.product_command_service.Dto.toDto.*;
import com.ecom.product_command_service.Entity.Category.Category;
import com.ecom.product_command_service.Entity.OperationType;
import com.ecom.product_command_service.Entity.Products.Product;
import com.ecom.product_command_service.Entity.Products.ProductVariant;
import com.ecom.product_command_service.Exception.Category.CategoryNotFoundException;

import com.ecom.product_command_service.Exception.Product.ProductNotFoundException;
import com.ecom.product_command_service.Repository.*;
import com.ecom.product_command_service.Service.BatchService.OperationBatchService;
import com.ecom.product_command_service.Service.Interface.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp  implements ProductService {
    private final ProductMapper productMapper;

    @Autowired

    private final OperationBatchService batchService;

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductVariantRepository productVariantRepository;
    @Autowired
    private final ProductImageRepository productImageRepository;
    @Autowired
    private final VariantImageRepository variantImageRepository;

    @Override
    public List<ProductResponse> getAllProductsByStoreId(UUID storeId) {
        return productRepository.findByStoreId(storeId)
                .stream()
                .filter(Objects::nonNull)
                .map(productMapper::entityToProduct)
                .collect(Collectors.toList());
    }


    @Override
    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->
                        new ProductNotFoundException("Product not found "+ productId));

        return productMapper.entityToProduct(product);
    }


    @Override
    public List<ProductResponse> getAllProductsByCategoryIdAndStoreId(UUID categoryId, UUID storeId) {
//        return productRepository.findAllByCategoryIdAndStoreId(categoryId, storeId)
//                .stream()
//                .map(productMapper::entityToProduct)
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<ProductWithCatagoryResponse> getAllProductsByStoreIdWithCategories(UUID storeId) {

        return productRepository
                .findAllByStoreIdWithCategories(storeId)
                .stream()
                .map(productMapper::entityToProductWithCategory)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest productRequest) {


    List<UUID> categoryListObjects = productRequest.getCategories().stream().map((category)->
            category.getCategories_id())
            .collect(Collectors.toList());

    List<Category> categoryList = categoryRepository.findAllById(categoryListObjects).stream()
            .filter(Objects::nonNull)
            .toList();

        System.out.println("categoryList  -> "+ categoryList.toString());

        Product product = productMapper.requestToProductsWithCategory(productRequest,  categoryList);

        Product savedProduct = productRepository.save(product);
        if(savedProduct.getProductId() != null){
//            batchService.addOperation(
//                    OperationObject
//                            .builder()
//                            .operationType(OperationType.ADD_PRODUCTS.toString())
//                            .operationObject(savedProduct)
//                            .build()
//            );

            setBatchService(OperationType.ADD_PRODUCTS, ProductDTO.builder()
                    .productId(savedProduct.getProductId())
                    .storeId(savedProduct.getStoreId())
                    .productName(savedProduct.getProductName())
                    .description(savedProduct.getDescription())
                    .price(savedProduct.getPrice())
                    .compareAtPrice(savedProduct.getCompareAtPrice())
                    .quantity(savedProduct.getQuantity())
                    .inStock(savedProduct.getInStock())
                    .offerVal(savedProduct.getOfferVal())
                    .published(savedProduct.getPublished())
                    .archived(savedProduct.getArchived())
                    .tags(savedProduct.getTags())
                    .customFields(savedProduct.getCustomFields())
                    .images(savedProduct.getProductImages().stream().map( productImage ->
                         ProductImageDTO.builder()
                                .productImageId(productImage.getProductImageId())
                                .imageUrl(productImage.getImageUrl())
                                .imagePosition(productImage.getImagePosition())
                                .build()
                    ).collect(Collectors.toList()))
                    .categories(categoryList.stream().map(category ->
                            CategoryDTO.builder()
                                    .storeId(category.getStoreId())
                                    .categories_id(category.getCategoryId())
                                    .categoryName(category.getCategoryName())
                            .build()).collect(Collectors.toList()))
                    .createDate(savedProduct.getCreateDate())
                    .updateDate(savedProduct.getUpdateDate())
                    .build());
        }

        return productMapper.entityToProduct(product);
    }


    @Override
    @Transactional
    public void addCategoriesToProduct(UUID productId, List<UUID> categoryIds) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found id :" + productId));

        List<Category> categories= categoryRepository.findAllById(categoryIds);

        product.getCategories().addAll(categories.stream()
                .filter(category -> !product.getCategories().contains(category))
                .collect(Collectors.toList()));
        productRepository.save(product);
        setBatchService(OperationType.ADD_LIST_CATEGORY_PRODUCTS,
                WriteListCategoryProduct.builder()
                        .productId(productId)
                        .category(categories)
                        .build());

    }






    @Override
    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductCreateRequest productDetails) {

        Product product =  productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Product savedProduct = productRepository.save(productMapper.requestToProduct(productDetails));
        if(savedProduct.getProductId() != null){
//            batchService.addOperation(
//                    OperationObject
//                            .builder()
//                            .operationType(OperationType.UPDATE_PRODUCTS.toString())
//                            .operationObject(savedProduct)
//                            .build()
//            );
            setBatchService(OperationType.UPDATE_PRODUCTS, ProductDTO.builder()
                    .productId(savedProduct.getProductId())
                    .storeId(savedProduct.getStoreId())
                    .productName(savedProduct.getProductName())
                    .description(savedProduct.getDescription())
                    .price(savedProduct.getPrice())
                    .compareAtPrice(savedProduct.getCompareAtPrice())
                    .quantity(savedProduct.getQuantity())
                    .inStock(savedProduct.getInStock())
                    .offerVal(savedProduct.getOfferVal())
                    .published(savedProduct.getPublished())
                    .archived(savedProduct.getArchived())
                    .tags(savedProduct.getTags())
                    .customFields(savedProduct.getCustomFields())
                    .images(savedProduct.getProductImages().stream().map( productImage ->
                            ProductImageDTO.builder()
                                    .productImageId(productImage.getProductImageId())
                                    .imageUrl(productImage.getImageUrl())
                                    .imagePosition(productImage.getImagePosition())
                                    .build()
                    ).collect(Collectors.toList()))
                    .categories(savedProduct.getCategories().stream().map(category ->
                            CategoryDTO.builder()
                                    .storeId(category.getStoreId())
                                    .categories_id(category.getCategoryId())
                                    .categoryName(category.getCategoryName())
                                    .build()).collect(Collectors.toList()))
                    .createDate(savedProduct.getCreateDate())
                    .updateDate(savedProduct.getUpdateDate())
                    .build());
        }

        return productMapper.entityToProduct(savedProduct);
    }



    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
//        batchService.addOperation(
//                OperationObject.builder()
//                        .operationType(OperationType.DELETE_PRODUCTS.toString())
//                        .operationObject(productId)
//                        .build()
//        );
        setBatchService(OperationType.DELETE_PRODUCTS,
                DeleteProduct.builder()
                        .productId(productId)
                        .build());
    }



    @Override
    @Transactional
    public void removeCategoryFromProduct(UUID productId, UUID categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("Product not found id : " + productId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
        product.getCategories().remove(category);
        productRepository.save(product);

//        batchService.addOperation(
//                OperationObject.builder()
//                        .operationType(OperationType.DELETE_CATEGORY_PRODUCTS.toString())
//                        .operationObject(categoryId)
//                        .build()
//        );
setBatchService(OperationType.DELETE_CATEGORY_PRODUCTS,
        DeleteCategoryProduct.builder()
                .categoryId(categoryId)
                .productId(productId)
                .build());
    }



    @Override
    public List<Product> findAllByStoreIdWithCategoriesAndVariants(UUID storeId) {

//        return productRepository
//                .findAllByStoreIdWithCategoriesAndVariants(storeId);

        return null;
    }

    @Override
    public Product findByProductIdWithCategoriesAndVariants(UUID productId) {

//        return productRepository
//                .findByProductIdWithCategoriesAndVariants(productId);
        return null;
    }

    @Override
    public List<Product> findAllByCategoryIdWithCategoriesAndVariants(UUID categoryId) {
//        return productRepository
//                .findAllByCategoryIdWithCategoriesAndVariants(categoryId);
        return null;
    }



    private void setBatchService(OperationType operationType, Object object){
        batchService.addOperation(
                OperationObject.builder()
                        .operationType(operationType.toString())
                        .operationObject(object)
                        .build()
        );
    }

}
