package com.example.retail_system_api.dto.response;

import com.example.retail_system_api.enums.OrdersStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersResponseDTO {

    private Long id;
    private String customerName;
    private LocalDateTime orderDate; //CreateAt
    private BigDecimal totalAmount;
    private List<OrderItemsResponseDTO> items; // list of order items
    private OrdersStatus status;
}


