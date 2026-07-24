package com.ecommerce.sb_ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min = 3,message = "Pname must contain min 3 characters")
    private String productName;

    @NotBlank
    @Size(min = 10,message = "Description must contain min 10 characters")
    private String description;
    private Integer quantity;
    private Double price;
    private Double specialPrice;
    private Double discount;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
