package com.ecom.product_command_service.Dto.OperationDtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteCategoryProduct {
   private UUID productId;
   private UUID categoryId;
}
