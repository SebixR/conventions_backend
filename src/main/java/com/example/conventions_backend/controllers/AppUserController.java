package com.example.conventions_backend.controllers;

import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.services.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AppUserController {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;

    public AppUserController(AppUserService appUserService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostMapping("public/signupUser")
//    public ResponseEntity<?> signupUser(@RequestBody AppUser newUser) {
//        boolean emailExists = appUserService.checkIfEmailExists(newUser.getEmail());
//        if (emailExists) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
//        }
//
//        AppUser appUser = new AppUser();
//        appUser.setFirstName(newUser.getFirstName());
//        appUser.setLastName(newUser.getLastName());
//        appUser.setEmail(newUser.getEmail());
//        appUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
//        appUser.setRole(newUser.getRole());
//
//        AppUser savedUser = appUserService.saveUser(appUser);
//
//        return ResponseEntity.ok(savedUser);
//    }
//
//    @PostMapping("public/loginUser")
//    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
//        boolean emailExists = appUserService.checkIfEmailExists(loginRequest.getEmail());
//        if (emailExists) {
//            Optional<AppUser> appUser = appUserService.getAppUserByEmail(loginRequest.getEmail());
//            if (appUser.isPresent()) {
//                var userObj = appUser.get();
//                if (userObj.getPassword().equals(passwordEncoder.encode(loginRequest.getPassword()))) {
//                    return ResponseEntity.ok("Login successful");
//                }
//            }
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//
//        return ResponseEntity.ok("Login successful");
//    }
}
