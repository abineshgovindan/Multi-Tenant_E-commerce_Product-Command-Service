package com.ecom.product_command_service.Exception.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductNotFoundException extends RuntimeException {
    private final String msg;
}
