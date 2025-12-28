package com.example.retrofit_user_app_build.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {

    private Integer status;
    private String message;
    private T data;

}
