package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.PasswordChangeDto;
import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.services.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class AppUserController {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AppUserController(AppUserService appUserService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

    @GetMapping("auth/getAppUserById/{id}")
    public ResponseEntity<AppUser> getAppUserById(@PathVariable("id") Long id) {
        try {
            AppUser appUser = appUserService.getAppUserById(id);
            return ResponseEntity.ok(appUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/updateAppUser")
    public ResponseEntity<AppUser> updateAppUser(@RequestBody AppUser updatedAppUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Optional<AppUser> userOptional = appUserService.getAppUserByEmail(userEmail);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AppUser appUser = userOptional.get();
        appUser.setFirstName(updatedAppUser.getFirstName());
        appUser.setLastName(updatedAppUser.getLastName());
        appUser.setEmail(updatedAppUser.getEmail());

        AppUser updatedAppUserData = appUserService.saveUser(appUser);

        return ResponseEntity.ok(updatedAppUserData);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<AppUser> updateAppUserPassword(@RequestBody PasswordChangeDto passwordChangeDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Optional<AppUser> userOptional = appUserService.getAppUserByEmail(userEmail);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AppUser appUser = userOptional.get();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            passwordChangeDto.getEmail(), passwordChangeDto.getOldPassword()
                    )
            );
        } catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }


        appUser.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));

        AppUser updatedAppUserData = appUserService.saveUser(appUser);

        return ResponseEntity.ok(updatedAppUserData);
    }

}
