package com.example.retrofit_user_app_build.repo;

import com.example.retrofit_user_app_build.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersModel, Integer> {

    boolean existsByEmail(String email);

}
