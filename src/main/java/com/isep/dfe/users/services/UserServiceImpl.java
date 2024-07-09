package com.isep.dfe.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isep.dfe.common.CommonResponse;
import com.isep.dfe.users.models.dtos.ChangePasswordOnRecoveryRequest;
import com.isep.dfe.users.models.dtos.ChangePasswordRequest;
import com.isep.dfe.users.models.dtos.ChangePasswordResponse;
import com.isep.dfe.users.models.dtos.FirebaseTokenRequest;
import com.isep.dfe.users.models.dtos.UpdateInfosRequest;
import com.isep.dfe.users.models.dtos.UserResponse;
import com.isep.dfe.users.models.entities.User;
import com.isep.dfe.users.models.enums.AccountState;
import com.isep.dfe.users.repositories.UserRepository;


import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private String getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ChangePasswordResponse.builder()
                    .message("Le mot de passe actuel est incorrect")
                    .success(false)
                    .build();
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            return ChangePasswordResponse.builder()
                    .message("Les mot de passes ne correspondent pas")
                    .success(false)
                    .build();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return ChangePasswordResponse.builder()
                .success(true)
                .build();
    }

}
