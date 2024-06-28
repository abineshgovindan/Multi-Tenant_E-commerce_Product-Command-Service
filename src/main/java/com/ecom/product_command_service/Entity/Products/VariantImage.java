package com.ecom.product_command_service.Entity.Products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "variant_images")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class VariantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "variant_image_id")
    private UUID variantImageId;


    @ManyToOne
    @JoinColumn(name = "variant_id", referencedColumnName = "variant_id")
    private ProductVariant productVariant;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_position")
    private Integer imagePosition;
}

