package com.ecommerce.sb_ecommerce.service;

import com.ecommerce.sb_ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
    String updateCategory(Category category, Long categoryId);
}
