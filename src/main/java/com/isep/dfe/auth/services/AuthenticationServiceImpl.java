package com.isep.dfe.auth.services;

import com.isep.dfe.auth.models.dtos.AuthenticationRequest;
import com.isep.dfe.auth.models.dtos.AuthenticationResponse;
import com.isep.dfe.auth.models.dtos.RegistrationRequest;
import com.isep.dfe.tokens.models.Token;
import com.isep.dfe.tokens.models.enums.TokenType;
import com.isep.dfe.tokens.repositories.TokenRepository;
import com.isep.dfe.tokens.services.JwtService;
import com.isep.dfe.users.models.entities.User;
import com.isep.dfe.users.models.enums.AccountState;
import com.isep.dfe.users.models.enums.Role;
import com.isep.dfe.users.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest request) {

        if (request.getUsername() == null || request.getFirstName() == null || request.getPassword() == null
                || request.getLastName() == null) {
            return AuthenticationResponse.builder().message("Merci de renseigner tous les champs").build();
        }

        if (request.getUsername() == "" || request.getFirstName() == "" || request.getPassword() == ""
                || request.getLastName() == "") {
            return AuthenticationResponse.builder().message("Merci de renseigner tous les champs").build();
        }

        Optional<User> checkIfExists = repository.findByUsername(request.getUsername());

        if (checkIfExists.isPresent()) {
            return AuthenticationResponse.builder().message("Un compte est déjà associé à l'adresse email renseigné")
                    .build();
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = repository.save(user);
        String jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .accessToken(jwtToken)
                .nextTransition("HOME")
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());
        AuthenticationResponse response = null;
        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = repository.findByUsername(request.getUsername())
                    .orElseThrow();


            String jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            response = AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .nextTransition("HOME")
                    .role(user.getRole()).build();

        } catch (Exception ex) {
            response = AuthenticationResponse.builder()
                    .message("Nom d'utilisateur ou mot de passe invalide")
                    .build();
        }
        return response;
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
