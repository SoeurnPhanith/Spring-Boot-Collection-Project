package com.example.retail_system_api.service.impl;

import com.example.retail_system_api.dto.request.OrdersRequestDTO;
import com.example.retail_system_api.dto.response.OrdersResponseDTO;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.OrdersEntity;
import com.example.retail_system_api.entity.ProductsEntity;
import com.example.retail_system_api.enums.OrdersStatus;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.impl.OrdersMapperImpl;
import com.example.retail_system_api.repo.CustomerRepository;
import com.example.retail_system_api.repo.OrdersRepository;
import com.example.retail_system_api.repo.ProductRepository;
import com.example.retail_system_api.repo.StockRepository;
import com.example.retail_system_api.service.OrdersService;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private OrdersMapperImpl ordersMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> addOrders(OrdersRequestDTO dto) {

        //1.get user id
        CustomersEntity customers = customerRepository.findById(dto.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("customer not found!")
        );

        //2. map data DTO → Entity
        OrdersEntity order = ordersMapper.orderDtoToEntity(dto,customers);

        //3.save order
        OrdersEntity savedOrder = orderRepo.save(order);

        //4.map data Entity -> DTO
        OrdersResponseDTO response = ordersMapper.orderEntityToDto(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
            201, "created order success", response
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrdersResponseDTO>>> checkOrderHistory() {
        //1.get all data from entity
        List<OrdersEntity> orders = orderRepo.findAll();

        //2.map data from entity to dto
        List<OrdersResponseDTO> dtoList = orders
                .stream()
                .map(o -> ordersMapper.orderEntityToDto(o))
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(200, "All orders", dtoList));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> cancelOrder(Long orderId) {
        //1.find order data by id
        OrdersEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        //2.update status to cancel
        order.setStatus(OrdersStatus.CANCELLED);

        //3.save it to db
        OrdersEntity updated = orderRepo.save(order);

        return ResponseEntity.ok(new ApiResponse<>(200, "Order cancelled", ordersMapper.orderEntityToDto(updated)));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> completeOrder(Long orderId) {
        //1.find order product by id
        OrdersEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        //2.update stock
        order.getItems().forEach(item -> {
            ProductsEntity product = item.getProduct();
            int remaining = product.getStock().getStockQty() - item.getQuantity();
            if(remaining < 0) throw new RuntimeException("Not enough stock for " + product.getProductName());

            product.getStock().setStockQty(remaining);
            stockRepository.save(product.getStock());
        });

        // 3.update Status
        order.setStatus(OrdersStatus.COMPLETED);
        OrdersEntity updated = orderRepo.save(order);

        return ResponseEntity.ok(new ApiResponse<>(200, "Order completed", ordersMapper.orderEntityToDto(updated)));
    }

}
