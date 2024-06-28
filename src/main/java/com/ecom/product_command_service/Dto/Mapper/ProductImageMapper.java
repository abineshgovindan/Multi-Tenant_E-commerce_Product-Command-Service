package com.ecom.product_command_service.Dto.Mapper;

import com.ecom.product_command_service.Dto.toDto.ProductImageDTO;
import com.ecom.product_command_service.Entity.Products.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {
    public ProductImageDTO toDTO(ProductImage image){
        return  ProductImageDTO.builder()
                .imageUrl(image.getImageUrl())
                .imagePosition(image.getImagePosition())
                .build();
    }
    public ProductImage toEntity(ProductImageDTO image){
        return  ProductImage.builder()
                .imageUrl(image.getImageUrl())
                .imagePosition(image.getImagePosition())
                .build();
    }


}
