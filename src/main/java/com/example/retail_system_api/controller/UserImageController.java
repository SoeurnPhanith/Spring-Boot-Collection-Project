package com.example.retail_system_api.controller;

import com.example.retail_system_api.entity.UsersImageEntity;
import com.example.retail_system_api.service.impl.UserImageServiceImpl;
import com.example.retail_system_api.utils.ApiResponse;
import com.example.retail_system_api.utils.BaseEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(BaseEndPoint.endPoint + "/user/images")
public class UserImageController {

    @Autowired
    private UserImageServiceImpl service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsersImageEntity>>> showAllProfile(){
        return service.showAllProfile();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsersImageEntity>> findProfileById(
            @PathVariable Long id
    ){
        return service.showProfileById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsersImageEntity>> updateProfile(
           @Valid @ModelAttribute UsersImageEntity usersImage,
           @Valid @PathVariable Long id,
           @Valid @RequestParam("image") MultipartFile file
    )throws IOException {
        return service.updateProfile(usersImage, id, file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> removeUserImage(
            @PathVariable Long id
    ){
        return service.removeProfile(id);
    }

}
