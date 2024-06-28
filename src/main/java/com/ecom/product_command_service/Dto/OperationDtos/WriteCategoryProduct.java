package com.ecom.product_command_service.Dto.OperationDtos;

import com.ecom.product_command_service.Entity.Category.Category;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class WriteCategoryProduct {
   private UUID productId;
   private Category category;
}
