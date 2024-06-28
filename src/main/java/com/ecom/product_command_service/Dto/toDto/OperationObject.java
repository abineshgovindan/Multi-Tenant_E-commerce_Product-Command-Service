package com.ecom.product_command_service.Dto.toDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationObject {
    private String operationType;
    private Object operationObject;
}
