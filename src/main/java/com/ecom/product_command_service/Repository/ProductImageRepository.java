package com.ecom.product_command_service.Repository;

import com.ecom.product_command_service.Entity.Products.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
}
