package com.ecom.product_command_service.Repository;

import com.ecom.product_command_service.Dto.Response.ProductResponse;
import com.ecom.product_command_service.Entity.Products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByStoreId(UUID storeId);

    @Query("SELECT  p FROM Product p LEFT JOIN FETCH p.categories WHERE p.storeId = :storeId")
    List<Product> findAllByStoreIdWithCategories(@Param("storeId") UUID storeId);

   // List<Product> findAllByCategoriesCategoryIdAndStoreId(UUID categoryId, UUID storeId);
//    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId AND p.storeId = :storeId")
//    List<Product> findAllByCategoryIdAndStoreId(@Param("categoryId") UUID categoryId,@Param("storeId") UUID storeId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories c WHERE c.categoryId = :categoryId AND p.storeId = :storeId")
    List<Product> findAllByCategoryIdAndStoreId(@Param("categoryId") UUID categoryId, @Param("storeId") UUID storeId);

//    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.categories c WHERE c.categoryId = :categoryId")
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    List<Product> findAllByCategoryId(@Param("categoryId") UUID categoryId);
//

    @Query("SELECT  p FROM Product p LEFT JOIN FETCH p.categories LEFT JOIN FETCH p.variants v LEFT JOIN FETCH v.categories WHERE p.storeId = :storeId")
    List<Product> findAllByStoreIdWithCategoriesAndVariants(UUID storeId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories LEFT JOIN FETCH p.variants v LEFT JOIN FETCH v.categories WHERE p.productId = :productId")
    Product findByProductIdWithCategoriesAndVariants(UUID productId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.categories LEFT JOIN FETCH p.variants v LEFT JOIN FETCH v.categories c WHERE c.categoryId = :categoryId")
    List<Product> findAllByCategoryIdWithCategoriesAndVariants(UUID categoryId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.categoryId = :categoryId")
    List<Product> findAllByCategoriesId(@Param("categoryId") UUID categoryId);

}

