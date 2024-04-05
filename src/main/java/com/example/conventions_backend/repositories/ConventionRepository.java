package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Convention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConventionRepository extends JpaRepository<Convention, Long> {

    List<Convention> getConventionByName(String name);
}
