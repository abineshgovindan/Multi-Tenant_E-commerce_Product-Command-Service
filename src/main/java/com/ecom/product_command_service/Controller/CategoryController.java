package com.ecom.product_command_service.Controller;

import com.ecom.product_command_service.Dto.Request.CategoryAddRequest;
import com.ecom.product_command_service.Dto.toDto.CategoryListObject;
import com.ecom.product_command_service.Entity.Category.Category;
import com.ecom.product_command_service.Exception.Category.CategoryNotFoundException;
import com.ecom.product_command_service.Service.Interface.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping()
    public ResponseEntity<List<CategoryListObject>> addCategories(@RequestBody CategoryAddRequest request){
        List<CategoryListObject> categoryListObjects = categoryService.addCategories(request);
        if(categoryListObjects.isEmpty()){
            throw new CategoryNotFoundException("No category associated  the storeId : "+ request.getStoreId());
        }
        return ResponseEntity.ok(categoryListObjects);
    }

    @GetMapping("/getAllCandS/{storeId}")
    public ResponseEntity<List<CategoryListObject>> getAllCategoryByStoreId(@PathVariable UUID storeId) {
        List<CategoryListObject> categoryListObjects = categoryService.getAllCategories(storeId);
        if(categoryListObjects.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryListObjects);
    }

    @GetMapping("/getC/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    @GetMapping("/delCP/{categoryId}")
    public ResponseEntity<?> removeCategoryFromProducts(@PathVariable UUID categoryId){
        categoryService.removeCategoryFromProducts(categoryId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/delCPv/{categoryId}")
    public ResponseEntity<?> removeCategoryFromProductVariant(@PathVariable UUID categoryId){
        categoryService.removeCategoryFromProductVariant(categoryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delCPPv/{categoryId}")
    public ResponseEntity<?> removeCategoryFromProductsAndProductVariants(@PathVariable UUID categoryId){
        categoryService.removeCategoryFromProductsAndProductVariants(categoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateC/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable  UUID categoryId,@RequestBody Category categoryDTO){
return ResponseEntity.ok().body(categoryService.updateCategory(categoryId, categoryDTO));
    }

     @DeleteMapping("/delC/{categoryId}")
     public ResponseEntity<?> deleteCategoryById(@PathVariable UUID categoryId){
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok().build();
     }
}



