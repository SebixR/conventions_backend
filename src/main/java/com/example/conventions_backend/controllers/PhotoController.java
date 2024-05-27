package com.example.conventions_backend.controllers;

import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.Photo;
import com.example.conventions_backend.services.ConventionService;
import com.example.conventions_backend.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class PhotoController {

    private final PhotoService photoService;
    private final ConventionService conventionService;

    @Autowired
    public PhotoController(PhotoService photoService, ConventionService conventionService) {
        this.photoService = photoService;
        this.conventionService = conventionService;
    }

    @PostMapping("auth/uploadPhoto")
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("file")MultipartFile file, @RequestParam("conventionId") Long conventionId) {
        try {
            Optional<Convention> conventionOptional = conventionService.getConvention(conventionId);
            if (conventionOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            Photo photo = photoService.storePhoto(file, conventionOptional.get());

            return ResponseEntity.ok(photo);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "auth/loadPhoto/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> loadPhoto(@PathVariable Long id) {
        try {
            Resource resource = photoService.loadPhoto(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
