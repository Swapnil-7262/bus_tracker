package com.swapnil.bustracker.controller;

import com.swapnil.bustracker.dto.ApiResponse;
import com.swapnil.bustracker.dto.LoginResponse;
import com.swapnil.bustracker.model.User;
import com.swapnil.bustracker.service.AuthService;
import com.swapnil.bustracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    // Use ResponseEntity and @Valid for validation
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
        User savedUser = authService.register(user);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "User registered successfully", savedUser),
                HttpStatus.CREATED // Status code 201
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody User user) {
        LoginResponse data = authService.login(user);
        return ResponseEntity.ok( // Status code 200
                new ApiResponse<>(true, "Login successful", data)
        );
    }
}
