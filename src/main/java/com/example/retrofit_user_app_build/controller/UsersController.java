package com.example.retrofit_user_app_build.controller;

import com.example.retrofit_user_app_build.dto.UserRequestDTO;
import com.example.retrofit_user_app_build.dto.UserResponseDTO;
import com.example.retrofit_user_app_build.model.ApiResponse;
import com.example.retrofit_user_app_build.service.UserServicesImpl;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping ("/app/users")
public class UsersController {

    @Autowired
    private UserServicesImpl userServices;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(
            @Valid @ModelAttribute UserRequestDTO usersModel,
            @Valid @RequestParam("image") MultipartFile imageFile
    ) throws IOException {
        return userServices.registerAccount(usersModel, imageFile);
    }

    @GetMapping
    public @NonNull ResponseEntity<ApiResponse<List<UserResponseDTO>>> showAllUser(){
        return userServices.showAllUser();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse<UserResponseDTO>> showUserById(
            @Valid @PathVariable Integer id
    ){
        return userServices.showUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @Valid @ModelAttribute UserRequestDTO user,
            @Valid @PathVariable Integer id,
            @Valid @RequestParam ("image") MultipartFile imageFile) throws IOException{
        return userServices.updateUser(user,id,imageFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeUser(
            @Valid @PathVariable Integer id
    ){
        return userServices.removeUser(id);
    }
}
