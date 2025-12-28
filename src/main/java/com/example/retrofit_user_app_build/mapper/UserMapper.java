package com.example.retrofit_user_app_build.mapper;

import com.example.retrofit_user_app_build.dto.UserRequestDTO;
import com.example.retrofit_user_app_build.dto.UserResponseDTO;
import com.example.retrofit_user_app_build.model.UsersModel;

public interface UserMapper {

    //dto to Model
    UsersModel dtoToModel (UserRequestDTO dto);

    //entity to dto
    UserResponseDTO modelToDto (UsersModel model);

}
