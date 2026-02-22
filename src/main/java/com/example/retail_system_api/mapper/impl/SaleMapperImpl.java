package com.example.retail_system_api.mapper.impl;

import com.example.retail_system_api.dto.request.SaleRequestDto;
import com.example.retail_system_api.dto.response.SaleItemResponseDTO;
import com.example.retail_system_api.dto.response.SaleResponseDto;
import com.example.retail_system_api.entity.*;
import com.example.retail_system_api.enums.PaymentMethod;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.mapper.SaleMapper;
import com.example.retail_system_api.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SaleMapperImpl implements SaleMapper {

    @Override
    public SaleEntity dtoToEntity(SaleRequestDto dto, OrdersEntity order, CustomersEntity customer) {
        SaleEntity sale = new SaleEntity();

        sale.setCustomer(customer);
        sale.setOrder(order);
        sale.setCreatedAt(LocalDateTime.now());
        sale.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        //map to sale item
        List<SaleItemsEntity> saleItems = order.getItems().stream().map(
                orderItem -> {
            SaleItemsEntity saleItem = new SaleItemsEntity();
            saleItem.setSale(sale);
            saleItem.setProduct(orderItem.getProduct());
            saleItem.setQuantity(orderItem.getQuantity());
            saleItem.setPrice(orderItem.getPrice());
            saleItem.setSubTotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

            return saleItem;
        }).toList();
        sale.setSalesItems(saleItems);

        //calculate total
        BigDecimal total = saleItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setTotalAmount(total);
        return sale;
    }

    @Override
    public SaleResponseDto entityToDto(SaleEntity sale) {
        SaleResponseDto dto = new SaleResponseDto();

        dto.setId(sale.getId());
        dto.setCustomerId(sale.getCustomer().getId());
        dto.setCustomerName(sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName());
        dto.setPaymentMethod(sale.getPaymentMethod().name());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setSaleDate(sale.getSaleDate());
        //map data from saleItem
        List<SaleItemResponseDTO> items = sale.getSalesItems().stream().map(item -> {

            SaleItemResponseDTO itemDto = new SaleItemResponseDTO();
            itemDto.setId(item.getId());
            itemDto.setProductName(item.getProduct().getProductName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            itemDto.setSubTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemDto;

        }).toList();
        dto.setItems(items);
        BigDecimal total = items.stream().map(
                i->i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotalAmount(total);
        return dto;
    }
}
