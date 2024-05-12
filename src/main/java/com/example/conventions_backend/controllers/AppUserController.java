package com.example.conventions_backend.controllers;

import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.services.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/getAppUser")
    public ResponseEntity<AppUser> getAppUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Optional<AppUser> appUserOptional = appUserService.getAppUserByEmail(userEmail);

        if (appUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            AppUser appUser = appUserOptional.get();

            AppUser responseAppUser = new AppUser();
            responseAppUser.setId(appUser.getId());
            responseAppUser.setEmail(appUser.getEmail());
            responseAppUser.setRole(appUser.getRole());
            responseAppUser.setFirstName(appUser.getFirstName());
            responseAppUser.setLastName(appUser.getLastName());

            return ResponseEntity.ok(responseAppUser);
        }
    }
}
