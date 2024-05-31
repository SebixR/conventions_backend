package com.example.conventions_backend.services;

import com.example.conventions_backend.dto.ConventionSpecifications;
import com.example.conventions_backend.dto.FilterRequestDto;
import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.ConventionStatus;
import com.example.conventions_backend.repositories.ConventionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Convention saveConvention(Convention convention) {
        return conventionRepository.save(convention);
    }

    public Page<Convention> getAllConventions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return conventionRepository.findAllByOrderByStatusAndStartDate(pageable);
    }

    public List<Convention> getConventionsByUser(Long id) {
        return conventionRepository.findAllByUserId(id);
    }

    public Page<Convention> getFilteredConventions(FilterRequestDto filterRequestDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return conventionRepository.findAll(ConventionSpecifications.withFilters(filterRequestDto), pageable);
    }

    public Page<Convention> searchConventions(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return conventionRepository.searchForConvention(keyword, pageable);
    }

    public void deleteConvention(Long id) {
        conventionRepository.deleteById(id);
    }

    public Convention blockConvention(Long id) {
        Optional<Convention> conventionOptional = conventionRepository.findById(id);
        if (conventionOptional.isEmpty())
            throw new EntityNotFoundException("Convention with id " + id + "wasn't found");

        Convention convention = conventionOptional.get();
        convention.setConventionStatus(ConventionStatus.BLOCKED);
        return conventionRepository.save(convention);
    }

    public Convention unblockConvention(Long id, ConventionStatus status) {
        Optional<Convention> conventionOptional = conventionRepository.findById(id);
        if (conventionOptional.isEmpty())
            throw new EntityNotFoundException("Convention with id " + id + "wasn't found");

        Convention convention = conventionOptional.get();
        convention.setConventionStatus(status);
        return conventionRepository.save(convention);
    }

}
