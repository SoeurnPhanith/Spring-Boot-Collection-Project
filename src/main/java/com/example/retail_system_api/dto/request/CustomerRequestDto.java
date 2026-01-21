package com.example.retail_system_api.dto.request;

import com.example.retail_system_api.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {


    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50)
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50)
    private String lastname;

    @Email(message = "Email must be valid")
    //@NotBlank(message = "Email cannot be blank")
    private String email;

  //  @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(
            regexp = "^(\\+?855|0)\\d{8,9}$",
            message = "Phone must be a valid Cambodia number"
    )
    private String phone;

    @NotNull
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255)
    private String address;

}
