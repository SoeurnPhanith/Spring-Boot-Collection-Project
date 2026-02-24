package com.example.retail_system_api.controller;

import com.example.retail_system_api.service.otp.OtpService;
import com.example.retail_system_api.utils.BaseEndPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseEndPoint.endPoint + "/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request-otp")
    public String requestOTP(@RequestParam String email){
        otpService.sentOTP(email);

        return "OTP is sending let check your email!";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword
    ){
        otpService.resetPassword(email, otp, newPassword);
        return "Password update success!";
    }



}
