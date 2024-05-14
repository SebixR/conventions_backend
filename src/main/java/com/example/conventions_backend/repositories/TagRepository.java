package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findTagByTag(String tag);
}
