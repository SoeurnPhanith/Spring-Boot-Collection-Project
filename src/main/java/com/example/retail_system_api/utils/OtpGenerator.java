package com.example.retail_system_api.utils;

import java.security.SecureRandom;

public class OtpGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String generate6Digits(){
        int num = random.nextInt(900000) + 100000;
        return String.valueOf(num);
    }
}
