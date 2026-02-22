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

        if(order.getStatus() != OrdersStatus.PENDING)
            throw new ResourceNotFoundException("Only order PENDING can cancel");

        //2.update status to cancel
        order.setStatus(OrdersStatus.CANCELLED);

        //3.save it to db
        OrdersEntity updated = orderRepo.save(order);

        return ResponseEntity.ok(new ApiResponse<>(200, "Order cancelled", ordersMapper.orderEntityToDto(updated)));
    }

    @Transactional
    public ResponseEntity<ApiResponse<OrdersResponseDTO>> checkoutOrder(Long orderId) {
        OrdersEntity order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if(order.getStatus() != OrdersStatus.PENDING)
            throw new ResourceNotFoundException("Only order PENDING can checkout");

        order.setStatus(OrdersStatus.CONFIRMED);
        orderRepo.save(order);

        return ResponseEntity.ok(new ApiResponse<>(200, "Order confirmed, please payment", ordersMapper.orderEntityToDto(order)));
    }

}
