package com.example.retrofit_user_app_build.service;

import com.example.retrofit_user_app_build.dto.UserRequestDTO;
import com.example.retrofit_user_app_build.dto.UserResponseDTO;
import com.example.retrofit_user_app_build.model.ApiResponse;
import com.example.retrofit_user_app_build.model.UsersModel;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserServices {

    @NonNull
    ResponseEntity<ApiResponse<UserResponseDTO>> registerAccount(UserRequestDTO user, MultipartFile imageFile) throws IOException;

    @NonNull
    ResponseEntity<ApiResponse<List<UserResponseDTO>>> showAllUser();

    ResponseEntity<ApiResponse<UserResponseDTO>> showUserById(Integer id);

    @NonNull
    ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(UserRequestDTO user, Integer id, MultipartFile imageFile) throws IOException;

    @NonNull
    ResponseEntity<ApiResponse<String>> removeUser(Integer id);


}
