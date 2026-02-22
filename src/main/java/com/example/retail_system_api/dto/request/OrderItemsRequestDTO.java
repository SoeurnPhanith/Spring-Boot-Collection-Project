package com.example.retail_system_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemsRequestDTO {

    @NotBlank
    @Positive
    private Long id;

    @NotBlank
    @Positive
    private Integer quantity;
}
