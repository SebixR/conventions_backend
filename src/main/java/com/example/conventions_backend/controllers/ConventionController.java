package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.ConventionDto;
import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.services.ConventionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ConventionController {

    private final ConventionService conventionService;

    public ConventionController(ConventionService conventionService) {
        this.conventionService = conventionService;
    }
    @GetMapping("/getConvention/{id}") //handles GET requests
    public ResponseEntity<Convention> getConventionById(@PathVariable("id") Long id) { //@PathVariable puts the id into the Long variable
        Optional<Convention> conventionOptional = conventionService.getConvention(id);
        if (conventionOptional.isPresent()) {
            return ResponseEntity.ok(conventionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addConvention")
    public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto) {

        Convention convention = new Convention();
        convention.setName(conventionDto.getName());
        convention.setStartDate(conventionDto.getStartDate());
        convention.setEndDate(conventionDto.getEndDate());
        convention.setDescription(conventionDto.getDescription());
        convention.setConventionStatus(conventionDto.getConventionStatus());

        //TODO all the relationships with the other tables

        Convention savedConvention = conventionService.saveConvention(convention);

        ConventionDto savedConventionDto = ConventionDto.fromConvention(savedConvention);

        return ResponseEntity.ok(savedConventionDto);
    }
}
