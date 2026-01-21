package com.example.retail_system_api.mapper.impl;

import com.example.retail_system_api.dto.request.OrdersRequestDTO;
import com.example.retail_system_api.dto.response.OrderItemsResponseDTO;
import com.example.retail_system_api.dto.response.OrdersResponseDTO;
import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.OrdersEntity;
import com.example.retail_system_api.entity.OrdersItemEntity;
import com.example.retail_system_api.entity.ProductsEntity;
import com.example.retail_system_api.enums.OrdersStatus;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.OrdersMapper;
import com.example.retail_system_api.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrdersMapperImpl implements OrdersMapper {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrdersEntity orderDtoToEntity(OrdersRequestDTO dto, CustomersEntity customers) {

        OrdersEntity order = new OrdersEntity();

        order.setCustomers(customers);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrdersStatus.PENDING);
        //  Map to OrderItems table
        List<OrdersItemEntity> orderItems = dto.getItems().stream().map(itemDto -> {
            if (itemDto.getId() == null) {
                throw new RuntimeException("Product ID cannot be null");
            }

            ProductsEntity product = productRepository.findById(itemDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getId()));

            //add data to ordersItem
            OrdersItemEntity item = new OrdersItemEntity();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrders(order);
            return item;
            }).toList();
        order.setItems(orderItems);

        // 🔹 Calculate total amount
        BigDecimal totalAmount = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);

        return order;
    }

    @Override
    public OrdersResponseDTO orderEntityToDto(OrdersEntity entity) {

        OrdersResponseDTO dto = new OrdersResponseDTO();

        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomers().getLastName());
        dto.setOrderDate(entity.getCreatedAt());

        BigDecimal totalAmount;
        List<OrderItemsResponseDTO> orderItemList = entity.getItems().stream().map(item -> {

            OrderItemsResponseDTO itemDto = new OrderItemsResponseDTO();

            BigDecimal price = item.getProduct().getPrice(); //get price from product table
            Integer qty = item.getQuantity();//get qty from stock
            BigDecimal subTotal = price.multiply(BigDecimal.valueOf(qty));

            itemDto.setId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getProductName());
            itemDto.setProductPrice(price);
            itemDto.setQuantity(qty);
            itemDto.setSubTotal(subTotal); // subtotal

            return itemDto;

        }).toList();

        // 🔹 calculate total amount
         totalAmount = orderItemList.stream()
                .map(OrderItemsResponseDTO::getSubTotal) //get sub total
                .reduce(BigDecimal.ZERO, BigDecimal::add); //sum subtotal of all product

        dto.setItems(orderItemList);
        dto.setTotalAmount(totalAmount);
        dto.setStatus(entity.getStatus());
        return dto;
    }

}
