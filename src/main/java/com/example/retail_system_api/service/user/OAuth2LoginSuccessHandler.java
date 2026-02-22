package com.example.retail_system_api.service.user;

import com.example.retail_system_api.entity.CustomersEntity;
import com.example.retail_system_api.entity.OAuth2Account;
import com.example.retail_system_api.entity.RoleEntity;
import com.example.retail_system_api.entity.UsersEntity;
import com.example.retail_system_api.repo.OAuth2AccountRepository;
import com.example.retail_system_api.repo.RoleRepository;
import com.example.retail_system_api.repo.CustomerRepository;
import com.example.retail_system_api.repo.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final OAuth2AccountRepository oAuth2AccountRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;

    public OAuth2LoginSuccessHandler(
            UserRepository userRepository,
            OAuth2AccountRepository oAuth2AccountRepository,
            RoleRepository roleRepository,
            CustomerRepository customerRepository
    ) {
        this.userRepository = userRepository;
        this.oAuth2AccountRepository = oAuth2AccountRepository;
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        //1. get userInformation from OAuth2 Provider (OAuth2 is object ដែលផ្ទុក data មកពី Google/GitcHub)
        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();

        //2.Detect Provider (user login from google or github or facebook)
        String provider = ((OAuth2AuthenticationToken) authentication)
                .getAuthorizedClientRegistrationId();

        //3. get provider id from detect provider when user choose for login
        Object providerObjId = null;
        if ("google".equals(provider)) {
            providerObjId = oAuthUser.getAttribute("sub"); //google having name(sub)
        } else {
            providerObjId = oAuthUser.getAttribute("id");
        }
        String providerUserId = providerObjId != null ? String.valueOf(providerObjId) : null;

        //4.get information from OAuthObj who get
        String email = oAuthUser.getAttribute("email");
        if (email == null) {
            email = oAuthUser.getAttribute("login") + "@github.com";
        }
        String name = oAuthUser.getAttribute("name");
        String picture = null;
        if ("google".equals(provider)) {
            picture = oAuthUser.getAttribute("picture");
        } else if ("github".equals(provider)) {
            picture = oAuthUser.getAttribute("avatar_url");
        }

        //5.find email of user or create account for user
        String finalEmail = email;
        UsersEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> createUser(finalEmail, name));

        //6.Save OAuth2 Account who get into db
        saveOAuth2Account(user, providerUserId, provider, email, name, picture);

        response.sendRedirect("/api/retail-system/v1/products");
    }

    private void saveOAuth2Account(UsersEntity user, String providerUserId, String provider, String email, String name, String picture) {

        oAuth2AccountRepository.findByProviderAndProviderUserId(provider, providerUserId)
                .orElseGet(() -> {

                    OAuth2Account acc = new OAuth2Account();
                    acc.setProvider(provider);
                    acc.setProviderUserId(providerUserId);
                    acc.setEmail(email);
                    acc.setPicture(picture);

                    // link to existing user (DON'T create new UsersEntity here)
                    acc.setUsers(user);

                    return oAuth2AccountRepository.save(acc);
                });
    }

    private UsersEntity createUser(String email, String name) {

        //set name into customer table
        CustomersEntity customers = new CustomersEntity();
        customers.setFirstName(name);

        // save customer first (because your mapping is not cascade)
        CustomersEntity savedCustomer = customerRepository.save(customers);

        //set Role into table Role
        // don't create new role every time, load existing role from DB
        RoleEntity role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found in tb_roles"));

        UsersEntity user = new UsersEntity();
        user.setEmail(email);
        user.setCustomer(savedCustomer);
        user.setRole(role);

        return userRepository.save(user);
    }
}