package com.example.retail_system_api.dto.request;

import com.example.retail_system_api.entity.OrdersItemEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequestDTO {

    @JsonProperty("customer_id")
    private Long customerId;

    private List<OrdersItemEntity> items; //when order user orders product na klas

}
