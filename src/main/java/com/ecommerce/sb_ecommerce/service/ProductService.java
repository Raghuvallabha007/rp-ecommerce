package com.ecommerce.sb_ecommerce.service;

import com.ecommerce.sb_ecommerce.model.Product;
import com.ecommerce.sb_ecommerce.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
