package com.example.retail_system_api.controller;

import com.example.retail_system_api.entity.UsersEntity;
import com.example.retail_system_api.service.user.UserAuthService;
import com.example.retail_system_api.utils.BaseEndPoint;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BaseEndPoint.endPoint + "/customers")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(
//            @RequestParam String email,
//            @RequestParam String password,
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//        userAuthService.login(email, password, request, response);
//        return ResponseEntity.ok("Login successful");
//    }

    @PostMapping("/login")
    public String login(@RequestBody UsersEntity users){
        return userAuthService.login(users);
    }

}