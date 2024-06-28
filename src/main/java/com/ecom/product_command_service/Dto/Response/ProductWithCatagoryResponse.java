package com.ecom.product_command_service.Dto.Response;


import com.ecom.product_command_service.Dto.toDto.CategoryListObject;
import com.ecom.product_command_service.Dto.toDto.ProductImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWithCatagoryResponse {
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
    private List<ProductImageDTO> images;
    private Map<String, String> customFields;
    private List<CategoryListObject> categories;
}
