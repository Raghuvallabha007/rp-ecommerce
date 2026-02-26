package com.ecommerce.sb_ecommerce.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "It cannot left blank")
    @Size(min = 3, message = "Category Name At least contain 3 characters")
    private String categoryName;
}
