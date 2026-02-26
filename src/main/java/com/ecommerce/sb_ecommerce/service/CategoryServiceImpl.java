package com.ecommerce.sb_ecommerce.service;

import com.ecommerce.sb_ecommerce.exception.APIException;
import com.ecommerce.sb_ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.sb_ecommerce.model.Category;
import com.ecommerce.sb_ecommerce.payload.CategoryDTO;
import com.ecommerce.sb_ecommerce.payload.CategoryResponse;
import com.ecommerce.sb_ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Direction.ASC, sortBy)
                : Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageableDetail = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageableDetail);
        List<Category> categories = categoryPage.getContent();
//        if (categories.size() == 0) {
//            throw new APIException("Categories are empty");
//        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalpages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
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
