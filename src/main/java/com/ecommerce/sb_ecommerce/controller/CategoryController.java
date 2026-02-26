package com.ecommerce.sb_ecommerce.controller;

import com.ecommerce.sb_ecommerce.config.AppConstants;
import com.ecommerce.sb_ecommerce.model.Category;
import com.ecommerce.sb_ecommerce.payload.CategoryDTO;
import com.ecommerce.sb_ecommerce.payload.CategoryResponse;
import com.ecommerce.sb_ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "PageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "PageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "SortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "SortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
            ){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categoryResponse);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO deleatedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deleatedCategory, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategories(@Valid @RequestBody CategoryDTO categoryDTO,
                                                     @PathVariable Long categoryId){
            CategoryDTO updatedCategoryInDB = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(updatedCategoryInDB, HttpStatus.OK);
    }

}
