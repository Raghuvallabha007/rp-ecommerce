package com.ecommerce.sb_ecommerce.service;

import com.ecommerce.sb_ecommerce.exception.APIException;
import com.ecommerce.sb_ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.sb_ecommerce.model.Category;
import com.ecommerce.sb_ecommerce.payload.CategoryDTO;
import com.ecommerce.sb_ecommerce.payload.CategoryResponse;
import com.ecommerce.sb_ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
//        if (categories.size() == 0) {
//            throw new APIException("Categories are empty");
//        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category  category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategoryFromDb != null) {
            throw new APIException("Category with name " + category.getCategoryName() + " already exists");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
            Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
           categoryRepository.delete(category);
           return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
