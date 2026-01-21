package com.example.retail_system_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsResponseDTO {

    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal subTotal;

}
