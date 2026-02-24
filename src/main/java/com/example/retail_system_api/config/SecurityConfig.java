package com.example.retail_system_api.config;

import com.example.retail_system_api.service.user.CustomUserService;
import com.example.retail_system_api.service.user.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private OAuth2LoginSuccessHandler oathSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager Bean is user for take data who load from CustomUserService
    //and Take Encoder password to Compare or Authenticated when user login
    //and check Roles Who are you, if doesn't having this bean spring security can't know
    //who you are when login and can't load data to compare or autheticated
    //AuthenticationManager Bean use for authenticate email/password by Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                //.httpBasic(h->{})
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                // Swagger
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()

                // PUBLIC endpoints (no login)
                // (Don't use "/customers/**" because it will make update/delete/list public too)
                //   only permit register + login
                .requestMatchers(HttpMethod.POST,
                        "/api/retail-system/v1/customers/register",
                        "/api/retail-system/v1/customers/login",
                        "/api/retail-system/v1/otp/request-otp",
                        "/api/retail-system/v1/otp/reset-password"
                ).permitAll()

                .requestMatchers(HttpMethod.GET,
                        "/api/retail-system/v1/products/**",
                        "/api/retail-system/v1/category/**",
                        "/api/retail-system/v1/sale/**"
                ).permitAll()

                //ADMIN only
                .requestMatchers(HttpMethod.POST, "/api/retail-system/v1/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/retail-system/v1/products/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/retail-system/v1/products/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/retail-system/v1/category").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/retail-system/v1/category/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/retail-system/v1/category/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/retail-system/v1/sale").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,  "/api/retail-system/v1/orders").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,  "/api/retail-system/v1/customers").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/retail-system/v1/customers/*").hasRole("ADMIN")

                // USER and ADMIN
                .requestMatchers("/api/retail-system/v1/user/images/**").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/retail-system/v1/customers/*").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/retail-system/v1/customers/*").hasAnyRole("USER","ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/retail-system/v1/orders").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT,  "/api/retail-system/v1/orders/*/checkout").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/retail-system/v1/orders/*/cancel").hasAnyRole("USER","ADMIN")

                // everything else must login
                .anyRequest().authenticated()
        );

        //add JWT filter to Security Config to work on it
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2 -> oauth2
                .successHandler(oathSuccessHandler)
        ); //enable OAuth2
        return http.build();
    }
}