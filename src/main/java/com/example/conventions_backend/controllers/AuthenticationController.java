package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.PasswordChangeDto;
import com.example.conventions_backend.services.AppUserService;
import com.example.conventions_backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AppUserService appUserService;

    @PostMapping("signupUser")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        boolean emailExists = appUserService.checkIfEmailExists(request.getEmail());
        if (emailExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
        }

        return ResponseEntity.ok(service.signup(request));
    }

    @PostMapping("loginUser")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(service.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}
