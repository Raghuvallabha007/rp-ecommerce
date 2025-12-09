package com.ecommerce.sb_ecommerce.service;

import com.ecommerce.sb_ecommerce.model.Category;
import com.ecommerce.sb_ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
//    private List<Category> categoryList = new ArrayList<>();
    private Long autoID = 1L;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();
       Category category = categories.stream()
                .filter(x -> x.getCategoryId().equals(categoryId))
                .findFirst()
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not found"));

           categoryRepository.delete(category);
           return "Successfully deleted the category with Category Id" + categoryId;
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        List<Category> categories = getAllCategories();
        Category existingCategory = categories.stream()
                .filter(x -> x.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found in the list"));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
        return "Updated Successfully";
    }
}
