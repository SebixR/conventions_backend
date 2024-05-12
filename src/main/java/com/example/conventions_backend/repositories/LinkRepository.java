package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
