package com.ecom.product_command_service.Dto.toDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDTO {

    private UUID variantId;
    private String sku;
    private String variantName;
    private BigDecimal variantPrice;
    private BigDecimal compareAtPrice;
    private int quantity;
    private Boolean archived;
    private Boolean inStock;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<String> tags;

    private List<VariantImageDTO> images;

    private List<CustomFieldDTO> options;

    private List<CategoryDTO> categories;




}
