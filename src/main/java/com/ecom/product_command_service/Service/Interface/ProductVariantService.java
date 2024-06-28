package com.ecom.product_command_service.Service.Interface;

import com.ecom.product_command_service.Entity.Products.ProductVariant;

import java.util.List;
import java.util.UUID;

public interface ProductVariantService {
    public  List<ProductVariant> getAllProductVariants(UUID productId);
    public ProductVariant getProductVariantById(UUID variantId);
    public ProductVariant createProductVariant(UUID productId, ProductVariant productVariant);

    public List<ProductVariant> createProductVariants(UUID productId, List<ProductVariant> productVariants);



    public ProductVariant updateProductVariant(UUID variantId, ProductVariant variantDetails);

    public List<ProductVariant> getAllProductVariantsByProductId(UUID productId);

    public ProductVariant addProductVariant(UUID productId, ProductVariant variant);

    public List<ProductVariant> addProductVariants(UUID productId, List<ProductVariant> variants);

    public void deleteProductVariant(UUID variantId);


}
