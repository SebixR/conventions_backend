package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByEmail(String email);
    Optional<AppUser> findById(Long id);
    boolean existsByEmail(String email);
}
