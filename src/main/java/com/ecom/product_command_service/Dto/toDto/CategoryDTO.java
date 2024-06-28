package com.ecom.product_command_service.Dto.toDto;


import com.ecom.product_command_service.Entity.Products.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private UUID categories_id;
    private UUID storeId;
    private String categoryName;

    private List<ProductDTO> products;
    private List<ProductVariant> productVariants;
}
