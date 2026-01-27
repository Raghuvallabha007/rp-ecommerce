package com.ecommerce.sb_ecommerce.repository;

import com.ecommerce.sb_ecommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank(message = "It cannot left blank") @Size(min = 3, message = "Category Name At least contain 3 characters") String categoryName);
}
