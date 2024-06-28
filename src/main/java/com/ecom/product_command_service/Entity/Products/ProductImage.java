package com.ecom.product_command_service.Entity.Products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "product_images")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_image_id")
    private UUID productImageId;


    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product ;


    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_position")
    private Integer imagePosition;
}
