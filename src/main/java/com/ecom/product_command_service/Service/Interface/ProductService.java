package com.ecom.product_command_service.Service.Interface;


import com.ecom.product_command_service.Dto.Request.ProductCreateRequest;
import com.ecom.product_command_service.Dto.Response.ProductResponse;
import com.ecom.product_command_service.Dto.Response.ProductWithCatagoryResponse;
import com.ecom.product_command_service.Entity.Products.Product;

import java.util.List;
import java.util.UUID;


public interface ProductService {
    public List<ProductResponse> getAllProductsByStoreId(UUID storeId);

    public ProductResponse getProductById(UUID productId);

    public List<ProductResponse> getAllProductsByCategoryIdAndStoreId(UUID categoryId, UUID storeId);
    ProductResponse createProduct(ProductCreateRequest product);

    public ProductResponse updateProduct(UUID productId, ProductCreateRequest productDetails);

    public void deleteProduct(UUID productId);

    public void addCategoriesToProduct(UUID productId, List<UUID> categoryIds);
    void removeCategoryFromProduct(UUID productId, UUID categoryId);

    List<Product> findAllByStoreIdWithCategoriesAndVariants(UUID storeId);

    Product findByProductIdWithCategoriesAndVariants(UUID productId);


    List<Product> findAllByCategoryIdWithCategoriesAndVariants(UUID categoryId);



    public List<ProductWithCatagoryResponse> getAllProductsByStoreIdWithCategories(UUID storeId);

}
