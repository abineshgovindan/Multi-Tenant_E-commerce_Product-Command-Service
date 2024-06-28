package com.ecom.product_command_service.Entity.Products;


import com.ecom.product_command_service.Entity.Category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tags")
    private List<String> tags;



    @ElementCollection
    @CollectionTable(name = "product_custom_fields", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "field_name")
    @Column(name = "field_value")
    private Map<String, String> customFields;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "compare_at_price")
    private BigDecimal compareAtPrice;



    @Column(name = "quantity")
    private int quantity;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "offerVal")
    private BigDecimal offerVal;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "inStock")
    private Boolean inStock;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> variants;


    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;


    @PrePersist
    public void onPrePersist() {
        this.setCreateDate(LocalDateTime.now());
        this.setUpdateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setUpdateDate(LocalDateTime.now());
    }




}


