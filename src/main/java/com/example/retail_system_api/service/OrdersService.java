package com.example.retail_system_api.service;

import com.example.retail_system_api.dto.request.OrdersRequestDTO;
import com.example.retail_system_api.dto.response.OrdersResponseDTO;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrdersService {

    // Create  order (status = PENDING)
    ResponseEntity<ApiResponse<OrdersResponseDTO>> addOrders(OrdersRequestDTO dto);

    ResponseEntity<ApiResponse<List<OrdersResponseDTO>>> checkOrderHistory();

    // Cancel a PENDING order (status -> CANCELLED)
    ResponseEntity<ApiResponse<OrdersResponseDTO>> cancelOrder(Long id);

    // Complete a PENDING order (status -> COMPLETED and subtract stock)
    ResponseEntity<ApiResponse<OrdersResponseDTO>> completeOrder(Long id);
}
