package com.ecom.product_command_service.Controller;


import com.ecom.product_command_service.Dto.Mapper.ProductImageMapper;
import com.ecom.product_command_service.Entity.Products.ProductVariant;
import com.ecom.product_command_service.Service.Interface.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/variants")
@RequiredArgsConstructor
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @GetMapping("/{productVariantId}")
    public List<ProductVariant> getAllProductVariants(@PathVariable UUID productVariantId) {
        List<ProductVariant> variants = productVariantService.getAllProductVariantsByProductId(productVariantId);
        return variants;
    }

    @GetMapping("/variant/{variantId}")
    public ProductVariant getProductVariantById(@PathVariable UUID variantId) {
        ProductVariant variant = productVariantService.getProductVariantById(variantId);
        return variant;
    }

    @PostMapping("/{productVariantId}")
    public ProductVariant createProductVariant(@PathVariable UUID productId, @RequestBody ProductVariant variantDto) {

       ProductVariant variant = productVariantService.addProductVariant(productId, variantDto);
        return variant;
    }


//    @PostMapping("/bulk/{productId}")
//    public List<ProductVariant> createProductVariants(@PathVariable UUID productId, @RequestBody List<ProductVariant> variantDtos) {
//        return productVariantService.addProductVariants(productId, variantDtos);
//
//    }


    @PostMapping("/bulk/{productId}")
    public ResponseEntity<List<ProductVariant>> addProductVariants(@PathVariable UUID productId, @RequestBody List<ProductVariant> variants) {
        List<ProductVariant> createdVariants = productVariantService.addProductVariants(productId, variants);
        return ResponseEntity.ok(createdVariants);
    }

    @PostMapping("{productId}/single")
    public ResponseEntity<ProductVariant> addProductVariant(@PathVariable UUID productId, @RequestBody ProductVariant variant) {
        ProductVariant createdVariant = productVariantService.addProductVariant(productId, variant);
        return ResponseEntity.ok(createdVariant);
    }

    @PutMapping("/{variantId}")
    public ProductVariant updateProductVariant(@PathVariable UUID variantId, @RequestBody ProductVariant variantDto) {
        return productVariantService.updateProductVariant(variantId, variantDto);
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteProductVariant(@PathVariable UUID variantId) {
        productVariantService.deleteProductVariant(variantId);
        return ResponseEntity.noContent().build();
    }

}
