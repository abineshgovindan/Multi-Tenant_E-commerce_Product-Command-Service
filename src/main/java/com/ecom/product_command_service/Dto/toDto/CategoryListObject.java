package com.ecom.product_command_service.Dto.toDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryListObject {
    private UUID categories_id;
    private String categoryName;
}
