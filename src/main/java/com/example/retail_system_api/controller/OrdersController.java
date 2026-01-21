package com.example.retail_system_api.controller;

import com.example.retail_system_api.dto.request.OrdersRequestDTO;
import com.example.retail_system_api.dto.response.OrdersResponseDTO;
import com.example.retail_system_api.service.impl.OrdersServiceImpl;
import com.example.retail_system_api.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    /*
        POST      /orders               → create (PENDING)
        PUT       /orders/{id}/complete → complete + subtract stock
        DELETE    /orders/{id}/cancel   → cancel (keep stock)
        GET       /orders               → history
    */

    @Autowired
    private OrdersServiceImpl ordersService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> order(
           @Valid @RequestBody OrdersRequestDTO dto
    ){
        return ordersService.addOrders(dto);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrdersResponseDTO>>> showAllOrdersHistory(){
        return ordersService.checkOrderHistory();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> completedOrders(
           @Valid @PathVariable Long id
    ){
        return ordersService.completeOrder(id);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> cancelledOrders(
            @Valid @PathVariable Long id
    ){
        return ordersService.cancelOrder(id);
    }

}
