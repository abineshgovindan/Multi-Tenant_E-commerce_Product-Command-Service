package com.ecom.product_command_service.Controller;

import com.ecom.product_command_service.Dto.Request.ProductCreateRequest;
import com.ecom.product_command_service.Dto.Response.ProductResponse;
import com.ecom.product_command_service.Dto.Response.ProductWithCatagoryResponse;
import com.ecom.product_command_service.Entity.Products.Product;

import com.ecom.product_command_service.Exception.Product.ProductNotFoundException;
import com.ecom.product_command_service.Service.Interface.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping()
    public ResponseEntity<ProductResponse> createProduct( @RequestBody ProductCreateRequest request) {

        ProductResponse product  =  productService.createProduct(request);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/{productId}/add-categories")
    public ResponseEntity<Void> addCategoriesToProduct(@PathVariable UUID productId, @RequestBody List<UUID> categoryIds) {
        productService.addCategoriesToProduct(productId, categoryIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId) {
        ProductResponse products = productService.getProductById(productId);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductResponse>> getAllProducts(@PathVariable UUID storeId) {
        List<ProductResponse> products = productService.getAllProductsByStoreId(storeId);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/store/{storeId}/with-categories")
    public ResponseEntity<List<ProductWithCatagoryResponse>> getAllProductsByStoreIdWithCategories(@PathVariable UUID storeId) {
        List<ProductWithCatagoryResponse> products = productService.getAllProductsByStoreIdWithCategories(storeId);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No Products are found for this store");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/store/{storeId}/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getAllProductsByCategoryIdAndStoreId(@PathVariable UUID storeId, @PathVariable UUID categoryId) {
        List<ProductResponse> products = productService.getAllProductsByCategoryIdAndStoreId(categoryId, storeId);
        return ResponseEntity.ok(products);
    }





    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID productId, @RequestBody ProductCreateRequest productDetails) {
        ProductResponse updatedProduct = productService.updateProduct(productId, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{productId}/remove-category/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromProduct(@PathVariable UUID productId, @PathVariable UUID categoryId) {
        productService.removeCategoryFromProduct(productId, categoryId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/store/{storeId}/with-categories-and-variants")
    public ResponseEntity<List<Product>> getAllProductsByStoreIdWithCategoriesAndVariants(@PathVariable UUID storeId) {
        return ResponseEntity.ok(productService.findAllByStoreIdWithCategoriesAndVariants(storeId));
    }

    @GetMapping("/{productId}/with-categories-and-variants")
    public ResponseEntity<Product> getProductByIdWithCategoriesAndVariants(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.findByProductIdWithCategoriesAndVariants(productId));
    }

    @GetMapping("/category/{categoryId}/with-categories-and-variants")
    public ResponseEntity<List<Product>> getAllProductsByCategoryIdWithCategoriesAndVariants(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(productService.findAllByCategoryIdWithCategoriesAndVariants(categoryId));
    }




}
