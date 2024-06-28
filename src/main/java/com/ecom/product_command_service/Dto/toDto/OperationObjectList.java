package com.ecom.product_command_service.Dto.toDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationObjectList {
    public List<OperationObject> operationObjects;
}
