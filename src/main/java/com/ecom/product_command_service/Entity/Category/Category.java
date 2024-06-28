package com.ecom.product_command_service.Entity.Category;

import com.ecom.product_command_service.Entity.Products.Product;
import com.ecom.product_command_service.Entity.Products.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "category_name")
    private String categoryName;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;
    @ToString.Exclude
    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY)
    private List<ProductVariant> productVariants;

}
