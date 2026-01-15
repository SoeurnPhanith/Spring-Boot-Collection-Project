package com.example.retrofit_user_app_build.mapper;

import com.example.retrofit_user_app_build.dto.UserRequestDTO;
import com.example.retrofit_user_app_build.dto.UserResponseDTO;
import com.example.retrofit_user_app_build.model.UsersModel;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UsersModel dtoToModel(UserRequestDTO dto) {
        UsersModel model = new UsersModel();

        model.setUsername(dto.getUsername());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());

        return model;
    }

    @Override
    public UserResponseDTO modelToDto (UsersModel model) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setUsername(model.getUsername());
        dto.setEmail(model.getEmail());
        dto.setImagePath(model.getImagePath());

        return dto;
    }
}
