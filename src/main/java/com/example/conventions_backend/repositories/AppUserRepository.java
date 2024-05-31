package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByEmail(String email);
    Optional<AppUser> findById(Long id);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AppUser> searchForAppUser(String keyword);
}
