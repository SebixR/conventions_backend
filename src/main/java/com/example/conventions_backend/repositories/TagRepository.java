package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
