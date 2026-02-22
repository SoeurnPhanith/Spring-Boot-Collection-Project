package com.example.retail_system_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
}
