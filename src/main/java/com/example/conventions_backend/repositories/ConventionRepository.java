package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Convention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ConventionRepository extends JpaRepository<Convention, Long>, JpaSpecificationExecutor<Convention> {

    List<Convention> findAllByUserId(Long id);
}
