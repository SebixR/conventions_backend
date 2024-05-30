package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.UserRole;
import com.example.conventions_backend.repositories.AppUserRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @Transactional
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + id + " not found"));
    }

    public AppUser saveUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public AppUser blockAppUser(Long id) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if (appUserOptional.isEmpty()) throw new NoSuchElementException("User with id " + id + " couldn't be found");
        AppUser appUser = appUserOptional.get();

        appUser.setRole(UserRole.BLOCKED);

        return appUserRepository.save(appUser);
    }

    public AppUser unblockAppUser(Long id) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);
        if (appUserOptional.isEmpty()) throw new NoSuchElementException("User with id " + id + " couldn't be found");
        AppUser appUser = appUserOptional.get();

        appUser.setRole(UserRole.USER);

        return appUserRepository.save(appUser);
    }

    public boolean checkIfEmailExists(String email) {
        return appUserRepository.existsByEmail(email);
    }

    public Optional<AppUser> getAppUserByEmail(String email) {
        return appUserRepository.findAppUserByEmail(email);
    }
}
