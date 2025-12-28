package com.example.retrofit_user_app_build.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "username can't be blank")
    @NotNull (message = "username can't be null")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "username must be only character")
    private String username;

    @Email(message = "email must be email")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$", message = "Password must contain 1 uppercase, 1 lowercase, 1 number, min 6 chars")
    private String password;

}
