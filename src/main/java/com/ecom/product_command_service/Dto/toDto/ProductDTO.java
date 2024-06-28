package com.ecom.product_command_service.Dto.toDto;


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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private UUID productId;
    private UUID storeId;
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private int quantity;
    private Boolean published;
    private BigDecimal offerVal;

    private Boolean archived;
    private Boolean inStock;
    private List<String> tags;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<ProductImageDTO> images;
    private Map<String, String> customFields;

    private List<CategoryDTO> categories;
    private List<ProductVariantDTO> variants;


}
