package com.ecommerce.sb_ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name= "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(Long categotyId, String categotyName) {
        this.categoryId = categotyId;
        this.categoryName = categotyName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categotyId) {
        this.categoryId = categotyId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categotyName) {
        this.categoryName = categotyName;
    }
}
