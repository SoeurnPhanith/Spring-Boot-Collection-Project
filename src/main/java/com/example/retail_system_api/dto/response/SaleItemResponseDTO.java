package com.example.retail_system_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItemResponseDTO {
    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
