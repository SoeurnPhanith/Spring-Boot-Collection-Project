package com.example.retrofit_user_app_build.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "tbl_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "images")
    private String imagePath;

}
