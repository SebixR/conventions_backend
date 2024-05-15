package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByConventionId(Long id);
}
