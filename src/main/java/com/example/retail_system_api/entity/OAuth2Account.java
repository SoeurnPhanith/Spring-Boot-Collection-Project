package com.example.retail_system_api.entity;

import com.example.retail_system_api.enums.Provider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "tb_oauth2_account")
public class OAuth2Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider; // GOOGLE, GITHUB, FACEBOOK

    @Column(name = "provider_id")
    private String providerUserId;

    private String email;
    private String picture;

    //Many provider ---->> One User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity users;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
