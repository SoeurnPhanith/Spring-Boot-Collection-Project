package com.example.retail_system_api.service.user;

import com.example.retail_system_api.entity.UsersEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    //private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public UserAuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /*This method is login with normal authentication store session */
//    public void login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password)
//        );
//
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//
//        // this saves to session (JSESSIONID)
//        securityContextRepository.saveContext(context, request, response);
//    }


    /*This method is login with JWT token (stateless)*/
    public String login(UsersEntity users){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        users.getEmail(),
                        users.getPassword()
                )
        );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(users.getEmail()); //generate token with email
        }
        return "Fail to Login";
    }
}