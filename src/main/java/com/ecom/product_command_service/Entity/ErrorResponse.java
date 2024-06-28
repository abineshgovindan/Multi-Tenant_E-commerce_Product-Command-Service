package com.ecom.product_command_service.Entity;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}