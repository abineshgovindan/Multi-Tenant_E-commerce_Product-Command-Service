package com.ecom.product_command_service.Exception.Category;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryNotFoundException extends RuntimeException {
    private final String msg;
}
