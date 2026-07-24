package com.ecommerce.sb_ecommerce.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank
    @Size(min = 3,message = "Pname must contain min 3 characters")
    private String productName;
    private String image;
    private Integer quantity;
    private Double price;
    private Double specialPrice;
    private Double discount;
    @NotBlank
    @Size(min = 3,message = "Description must contain min 3 characters")
    private String description;
}
