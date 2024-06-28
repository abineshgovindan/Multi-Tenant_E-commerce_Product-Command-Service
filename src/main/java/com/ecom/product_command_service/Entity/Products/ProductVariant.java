package com.ecom.product_command_service.Entity.Products;

import com.ecom.product_command_service.Entity.Category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "productVariant")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "variant_id")
    private UUID variantId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<VariantImage> variantImages;


    @Column(name = "sku")
    private String sku;

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "variant_price")
    private BigDecimal variantPrice;


    @Column(name = "compare_at_price")
    private BigDecimal compareAtPrice;


    @ElementCollection
    @CollectionTable(name = "variant_tags", joinColumns = @JoinColumn(name = "variant_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "variant_options", joinColumns = @JoinColumn(name = "variant_id"))
    @MapKeyColumn(name = "option_name")
    @Column(name = "option_value")
    private Map<String, String> options;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_variant_category",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;





    @Column(name = "quantity")
    private int quantity;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "inStock")
    private Boolean inStock;


    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;


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
