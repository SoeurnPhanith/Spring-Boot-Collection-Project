package com.example.retail_system_api.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItemsRequestDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

}

