package com.ecommerce.sb_ecommerce.repository;

import com.ecommerce.sb_ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
