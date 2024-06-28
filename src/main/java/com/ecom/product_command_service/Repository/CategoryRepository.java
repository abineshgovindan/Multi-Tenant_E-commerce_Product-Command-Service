package com.ecom.product_command_service.Repository;

import com.ecom.product_command_service.Entity.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByStoreId(UUID storeId);
    //List<Category> findByProductId(UUID productId);

}
