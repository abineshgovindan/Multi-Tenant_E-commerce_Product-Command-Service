package com.ecom.product_command_service.Repository;

import com.ecom.product_command_service.Entity.Products.Product;
import com.ecom.product_command_service.Entity.Products.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    @Query("SELECT DISTINCT pv FROM ProductVariant pv LEFT JOIN FETCH pv.categories WHERE pv.product.storeId = :storeId")
    List<ProductVariant> findAllByStoreIdWithCategories(@Param("storeId") UUID storeId);

//    List<ProductVariant> findAllByProduct_ProductId(UUID productId);

//    @Query("SELECT DISTINCT pv FROM ProductVariant pv LEFT JOIN FETCH pv.categories WHERE pv.product.storeId = :storeId AND pv.categories.categoryId = :categoryId")
//    List<ProductVariant> findAllByStoreIdAndCategoryId(UUID storeId, UUID categoryId);

//    List<ProductVariant> findAllByCategoriesId(UUID categoryId);
//
    List<ProductVariant> findByProductProductId(UUID productId);


}
