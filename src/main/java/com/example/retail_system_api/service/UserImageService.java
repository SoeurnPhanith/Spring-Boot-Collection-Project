package com.example.retail_system_api.service;

import com.example.retail_system_api.entity.UsersImageEntity;
import com.example.retail_system_api.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserImageService {

    //todo : make interface of User image
    ResponseEntity<ApiResponse<List<UsersImageEntity>>> showAllProfile();

    ResponseEntity<ApiResponse<UsersImageEntity>> showProfileById(Long id);

    ResponseEntity<ApiResponse<UsersImageEntity>> updateProfile
            (UsersImageEntity usersImage, Long id, MultipartFile file)throws IOException;

    ResponseEntity<ApiResponse<?>> removeProfile(Long id);

}
