package com.example.retail_system_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private String productName;

    private BigDecimal price;

    private String imageUrl;

    private String categoryName;

    private Integer stockQuantity;

    private String status;

    private LocalDateTime createdAt;
}
