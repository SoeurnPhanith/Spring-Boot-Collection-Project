package com.example.retail_system_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @JsonProperty("name")
    @NotBlank(message = "name can not blank | empty")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "name must be only character")
    private String categoryName;

}
