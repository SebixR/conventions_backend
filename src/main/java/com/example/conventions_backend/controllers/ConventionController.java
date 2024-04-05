package com.example.conventions_backend.controllers;

import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.services.ConventionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ConventionController {

    private ConventionService conventionService;

    public ConventionController(ConventionService conventionService) {
        this.conventionService = conventionService;
    }
    @GetMapping("/public/getConvention/{id}") //handles GET requests
    public ResponseEntity<Convention> getConventionById(@PathVariable("id") Long id) { //@PathVariable puts the id into the Long variable
        Optional<Convention> conventionOptional = conventionService.getConvention(id);
        if (conventionOptional.isPresent()) {
            return ResponseEntity.ok(conventionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
