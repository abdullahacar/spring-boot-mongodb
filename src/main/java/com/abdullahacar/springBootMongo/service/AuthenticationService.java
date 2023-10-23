package com.abdullahacar.springBootMongo.service;


import com.abdullahacar.springBootMongo.auth.config.JwtService;
import com.abdullahacar.springBootMongo.auth.config.UserDetailsImpl;
import com.abdullahacar.springBootMongo.dto.ApiResponse;
import com.abdullahacar.springBootMongo.dto.ApiResponseCode;
import com.abdullahacar.springBootMongo.dto.auth.AuthenticationResponse;
import com.abdullahacar.springBootMongo.dto.auth.RegisterRequest;
import com.abdullahacar.springBootMongo.dto.querymodel.Authorization.TokenQueryModel;
import com.abdullahacar.springBootMongo.dto.querymodel.Login.LoginQueryModel;
import com.abdullahacar.springBootMongo.entity.Login;
import com.abdullahacar.springBootMongo.entity.Token;
import com.abdullahacar.springBootMongo.repository.auth.TokenRepository;
import com.abdullahacar.springBootMongo.repository.auth.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ApiResponse<AuthenticationResponse> register(RegisterRequest request) {

        Optional<Login> byUsername = repository.findByUsername(request.getEmail());

        if (byUsername.isPresent()) {

            return ApiResponse.<AuthenticationResponse>builder()
                    .apiResponseCode(ApiResponseCode.WARNING)
                    .entity(AuthenticationResponse.builder()
                            .message("User already exists : " + request.getEmail())
                            .build())
                    .build();

        }

        var user = Login.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .username(request.getEmail())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(UserDetailsImpl.build(user));
        saveUserToken(savedUser, jwtToken);
        return ApiResponse.<AuthenticationResponse>builder()
                .apiResponseCode(ApiResponseCode.SUCCESSFUL)
                .entity(AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build())
                .build();
    }

    public ApiResponse<AuthenticationResponse> authenticate(RegisterRequest request) {

                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + request.getEmail()));
        var jwtToken = jwtService.generateToken(UserDetailsImpl.build(user));
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return ApiResponse.<AuthenticationResponse>builder()
                .apiResponseCode(ApiResponseCode.SUCCESSFUL)
                .entity(AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build())
                .build();
    }

    private void saveUserToken(Login user, String jwtToken) {
        var token = Token.builder()
                .loginId(user.getId())
                .issueDate(new Date())
                .tokenKey(jwtToken)
                .active(true)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Login user) {

        var validUserTokens = tokenRepository.getAllActiveTokensByLoginId(TokenQueryModel.builder().loginId(user.getId()).build());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setActive(false);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByUsername(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, UserDetailsImpl.build(user))) {
                var accessToken = jwtService.generateToken(UserDetailsImpl.build(user));
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
