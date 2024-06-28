package com.ecom.product_command_service.Service;

import com.ecom.product_command_service.Entity.Products.Product;
import com.ecom.product_command_service.Entity.Products.ProductVariant;
import com.ecom.product_command_service.Exception.Product.ProductNotFoundException;
import com.ecom.product_command_service.Exception.Product.ProductVariantNotFoundException;
import com.ecom.product_command_service.Repository.ProductRepository;
import com.ecom.product_command_service.Repository.ProductVariantRepository;
import com.ecom.product_command_service.Service.Interface.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImp implements ProductVariantService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public List<ProductVariant> getAllProductVariants(UUID productId) {
        return productVariantRepository.findByProductProductId(productId);
    }

    @Override
    public ProductVariant getProductVariantById(UUID variantId) {
        return productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductVariantNotFoundException("ProductVariant not found"));
    }

    @Override
    public ProductVariant createProductVariant(UUID productId, ProductVariant productVariant) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productVariant.setProduct(product);
        return productVariantRepository.save(productVariant);
    }

    @Override
    public List<ProductVariant> createProductVariants(UUID productId, List<ProductVariant> productVariants) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productVariants.forEach(variant -> variant.setProduct(product));
        return productVariantRepository.saveAll(productVariants);
    }




    @Override
    public ProductVariant updateProductVariant(UUID variantId, ProductVariant variantDetails) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ProductVariantNotFoundException("ProductVariant not found"));


        variant.setSku(variantDetails.getSku());
        variant.setVariantName(variantDetails.getVariantName());
        variant.setVariantPrice(variantDetails.getVariantPrice());
        variant.setCompareAtPrice(variantDetails.getCompareAtPrice());
        variant.setTags(variantDetails.getTags());
        variant.setOptions(variantDetails.getOptions());
        variant.setQuantity(variantDetails.getQuantity());
        variant.setArchived(variantDetails.getArchived());
        variant.setInStock(variantDetails.getInStock());


        return productVariantRepository.save(variant);
    }

    @Override
    public List<ProductVariant> getAllProductVariantsByProductId(UUID productId) {
        return productVariantRepository.findByProductProductId(productId);
    }

    @Override
    public ProductVariant addProductVariant(UUID productId, ProductVariant variant) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            variant.setProduct(product);
            return productVariantRepository.save(variant);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Override
    public List<ProductVariant> addProductVariants(UUID productId, List<ProductVariant> variants) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            variants.forEach(variant -> variant.setProduct(product));
            return productVariantRepository.saveAll(variants);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Override
    public void deleteProductVariant(UUID variantId) {
        productVariantRepository.deleteById(variantId);
    }
}
