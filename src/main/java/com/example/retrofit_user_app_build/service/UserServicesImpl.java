package com.example.retrofit_user_app_build.service;

import com.example.retrofit_user_app_build.dto.UserRequestDTO;
import com.example.retrofit_user_app_build.dto.UserResponseDTO;
import com.example.retrofit_user_app_build.mapper.UserMapperImpl;
import com.example.retrofit_user_app_build.model.ApiResponse;
import com.example.retrofit_user_app_build.model.UsersModel;
import com.example.retrofit_user_app_build.repo.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapperImpl mapper;

    @Transactional
    @Override
    public @NonNull ResponseEntity<ApiResponse<UserResponseDTO>> registerAccount(UserRequestDTO user, MultipartFile imageFile) throws IOException {
        //1. check exists User
        boolean existsUser = userRepo.existsByEmail(user.getEmail());
        if(existsUser)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>(
                            409,
                            "user is already exists",
                            null
                    )
            );

        //2.map data from dto -> entity or model
        UsersModel usersModel =  mapper.dtoToModel(user);
        if (imageFile != null && !imageFile.isEmpty()) {
            // ️save image file to folder uploads
            String uploadDir = System.getProperty("user.dir") + "/uploads/"; // get Image save to Path deal jg tuk
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); //get file name from machine when user choose it
            Path filePath = Paths.get(uploadDir + fileName); //full path of image

            // create folder if not exists
            Files.createDirectories(filePath.getParent());

            imageFile.transferTo(filePath.toFile()); //save file pii client to uploads directory

            // save imagePath to Database
            usersModel.setImagePath( "http://localhost:8080"+ "/uploads/" +fileName);

        }

        //3. save data to database
        UsersModel savedUser = userRepo.save(usersModel);

        //4. map data from model to dto
        UserResponseDTO dto = mapper.modelToDto(savedUser);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        201,
                        "user account created",
                        dto
                )
        );
    }

    @Transactional
    @Override
    public @NonNull ResponseEntity<ApiResponse<List<UserResponseDTO>>> showAllUser() {
        //1. Get all user
        List<UsersModel> userList = userRepo.findAll();
        if(userList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            404,
                            "user not found",
                            null
                    )
            );

        //2.map data from model -> dto
        List<UserResponseDTO> dtoList = new ArrayList<>();
        for(UsersModel user : userList){
            UserResponseDTO dto = mapper.modelToDto(user);
            //add to list
            dtoList.add(dto);
        }
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        "success",
                        dtoList
                )
        );

    }

    @Override
    public ResponseEntity<ApiResponse<UserResponseDTO>> showUserById(Integer id) {
        //1. get user by id
        Optional<UsersModel> findUserById = userRepo.findById(id);
        if(!findUserById.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            404,
                            "user not found",
                            null
                    )
            );

        UsersModel user = findUserById.get();

        //2.map data from entity to dto
        UserResponseDTO dto = mapper.modelToDto(user);
        return ResponseEntity.ok().body(
                    new ApiResponse<>(
                    200,
                    "success",
                    dto
                )
        );

    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(UserRequestDTO user, Integer id, MultipartFile imageFile) throws IOException{
        //1. find user
        Optional<UsersModel> findUser = userRepo.findById(id);
        if(!findUser.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            404,
                            "user not found to update",
                            null
                    )
            );

        //2. get data and update it
        UsersModel update = findUser.get();
        update.setUsername(user.getUsername());
        update.setEmail(user.getEmail());
        update.setPassword(user.getPassword());
        if (imageFile != null && !imageFile.isEmpty()) {
            // ️save image file to folder uploads
            String uploadDir = System.getProperty("user.dir") + "/uploads/"; // get Image save to Path deal jg tuk
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); //get file name from machine when user choose it
            Path filePath = Paths.get(uploadDir + fileName); //full path of image

            // create folder if not exists
            Files.createDirectories(filePath.getParent());

            imageFile.transferTo(filePath.toFile()); //save file pii client to uploads directory

            // save imagePath to Database
            update.setImagePath( "http://localhost:8080"+ "/uploads/" +fileName);
        }

        //3.save to database
        UsersModel save = userRepo.save(update);

        //4.map data from entity to dto
        UserResponseDTO dto = mapper.modelToDto(save);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        200,
                        "user update success",
                        dto
                )
        );
    }

    @Override
    public @NonNull ResponseEntity<ApiResponse<String>> removeUser(Integer id) {
        Optional<UsersModel> findUser = userRepo.findById(id);
        if(!findUser.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            404,
                            "users not found",
                            null
                    )
            );

        userRepo.deleteById(id);
        return ResponseEntity.ok().body(new ApiResponse<>(
                200,
                "success",
                "remove user success"
        ));
    }

}
