package com.ecommerce.sb_ecommerce.service;

import com.ecommerce.sb_ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.sb_ecommerce.model.Category;
import com.ecommerce.sb_ecommerce.model.Product;
import com.ecommerce.sb_ecommerce.payload.ProductDTO;
import com.ecommerce.sb_ecommerce.payload.ProductResponse;
import com.ecommerce.sb_ecommerce.repository.CategoryRepository;
import com.ecommerce.sb_ecommerce.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.images}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = getCategoryByIdOrThrow(categoryId);
        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("test.png");
        product.setCategory(category);
        double specialPrice =
                product.getPrice() - (product.getDiscount() * 0.01 * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        log.info("Get all products in service layer starts");
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = mapToProductDTOs(products);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        log.info("Getting all products in service layer- {}", productResponse.getContent());
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        log.info("Get searchByCategory in service layer with id {}", categoryId);
        Category category = getCategoryByIdOrThrow(categoryId);
        List<Product> products = productRepository.getProductByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = mapToProductDTOs(products);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        log.info("Get searchProductByKeyword in service layer with keyword {}", keyword);
        if (StringUtils.isNotEmpty(keyword)) {
           List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");
           List<ProductDTO> productDTOS = mapToProductDTOs(products);
           ProductResponse productResponse = new ProductResponse();
           productResponse.setContent(productDTOS);
           return productResponse;
        }
        return null;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        log.info("Update product in service layer with id {}", productId);
        Product product = modelMapper.map(productDTO, Product.class);
        Product getProductFromDb = getProductByIdOrThrow(productId);
        getProductFromDb.setPrice(product.getPrice());
        getProductFromDb.setSpecialPrice(product.getSpecialPrice());
        getProductFromDb.setProductName(product.getProductName());
        getProductFromDb.setQuantity(product.getQuantity());
        getProductFromDb.setDescription(product.getDescription());
        getProductFromDb.setDiscount(product.getDiscount());
        Product updatedProduct = productRepository.save(getProductFromDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        log.info("Delete product in service layer with id {}", productId);
        Product getProductFromDb = getProductByIdOrThrow(productId);
        productRepository.delete(getProductFromDb);
        return modelMapper.map(getProductFromDb, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        log.info("Update product image in service layer with id {}", productId);
        Product productFromDb = getProductByIdOrThrow(productId);
        String fileName = fileService.uploadImage(path, image);
        productFromDb.setImage(fileName);
        Product updatedProduct = productRepository.save(productFromDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private List<ProductDTO> mapToProductDTOs(List<Product> products) {
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    private Category getCategoryByIdOrThrow(Long categoryId) {
        log.info("getCategoryByIdOrThrow method - {} in service layer starts", categoryId);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "categoryId",
                                categoryId));
    }

    private Product getProductByIdOrThrow(Long productId) {
        log.info("getProductByIdOrThrow method - {} in service layer starts", productId);
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "productId",
                                productId));
    }
}
