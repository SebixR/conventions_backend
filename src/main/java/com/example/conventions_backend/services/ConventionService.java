package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.repositories.ConventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConventionService {

    private final ConventionRepository conventionRepository;

    @Autowired
    public ConventionService(ConventionRepository conventionRepository) {
        this.conventionRepository = conventionRepository;
    }

    public Optional<Convention> getConvention(Long id) {
        return conventionRepository.findById(id);
    }

    public List<Convention> getConventionByName(String name) {
        return conventionRepository.getConventionByName(name);
    }

}
