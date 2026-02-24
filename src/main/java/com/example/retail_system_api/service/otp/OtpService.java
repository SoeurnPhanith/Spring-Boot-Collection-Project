package com.example.retail_system_api.service.otp;

import com.example.retail_system_api.entity.OtpEntity;
import com.example.retail_system_api.entity.UsersEntity;
import com.example.retail_system_api.exception.DuplicateResourceException;
import com.example.retail_system_api.exception.ResourceNotFoundException;
import com.example.retail_system_api.repo.OtpRepository;
import com.example.retail_system_api.repo.UserRepository;
import com.example.retail_system_api.utils.OtpGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    public OtpService(OtpRepository otpRepository, UserRepository userRepository, EmailService emailService, PasswordEncoder encoder) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.encoder = encoder;
    }

    public void sentOTP(String email){
        userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("user not found!"));

        String otp = OtpGenerator.generate6Digits();
        LocalDateTime expire = LocalDateTime.now().plusMinutes(5);

        OtpEntity entity = new OtpEntity();
        entity.setEmail(email);
        entity.setOtpCode(otp);
        entity.setExpireTime(expire);

        //save to db
        otpRepository.save(entity);

        //sent to User
        emailService.sentOtp(email,otp);
    }

    public void resetPassword(String email, String otp, String newPassword){
        OtpEntity entity = otpRepository.findByEmailAndOtpCode(email,otp)
                .orElseThrow(()->new ResourceNotFoundException("Invalid OTP"));

        if(entity.isUsed()){
            throw new DuplicateResourceException("OTP is already used!");
        }

        if(entity.getExpireTime().isBefore(LocalDateTime.now())){
            throw new ResourceNotFoundException("OTP expired!");
        }

        UsersEntity user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("user not found!"));
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        entity.setUsed(true);
        otpRepository.save(entity);
    }
}
