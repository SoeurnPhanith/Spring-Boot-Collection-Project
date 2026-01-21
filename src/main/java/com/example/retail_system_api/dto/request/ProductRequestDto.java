package com.example.retail_system_api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "product name is required")
    @Size(min = 4, max = 30, message = "product name should be between 4 and 30 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "product name must contain only letters")
    private String productName;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than zero")
    private BigDecimal price;

    private String imageUrl;


    @NotNull(message = "category id is required")
    @Positive(message = "category id must be positive")
    private Long categoryId;

    @NotNull(message = "stock id is required")
    @Positive(message = "stock id must be positive")
    private Integer stockQty;

    @NotBlank(message = "description is required")
    private String description;
}
