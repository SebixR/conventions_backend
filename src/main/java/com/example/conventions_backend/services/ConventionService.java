package com.example.conventions_backend.services;

import com.example.conventions_backend.dto.ConventionSpecifications;
import com.example.conventions_backend.dto.FilterRequestDto;
import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.Photo;
import com.example.conventions_backend.repositories.ConventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Convention saveConvention(Convention convention) {
        return conventionRepository.save(convention);
    }

    public List<Convention> getAllConventions() {
        return conventionRepository.findAll();
    }

    public List<Convention> getConventionsByUser(Long id) {
        return conventionRepository.findAllByUserId(id);
    }

    public List<Convention> getFilteredConventions(FilterRequestDto filterRequestDto) {
        return conventionRepository.findAll(ConventionSpecifications.withFilters(filterRequestDto));
    }

    public void deleteConvention(Long id) {
        conventionRepository.deleteById(id);
    }

}
