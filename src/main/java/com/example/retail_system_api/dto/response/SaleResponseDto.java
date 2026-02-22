package com.example.retail_system_api.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponseDto {

    private Long id;
    private Long customerId;
    private String customerName;
    private BigDecimal totalAmount;
    private LocalDateTime saleDate;
    private String paymentMethod;
    private List<SaleItemResponseDTO> items;

}
