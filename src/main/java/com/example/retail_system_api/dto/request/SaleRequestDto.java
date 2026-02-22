package com.example.retail_system_api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SaleRequestDto     {

    private Long orderId;
    private String paymentMethod; // CASH, ABA, CARD
    private List<SaleItemsRequestDto> items = new ArrayList<>();



}
