package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.Photo;
import com.example.conventions_backend.repositories.ConventionRepository;
import com.example.conventions_backend.repositories.PhotoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {
    @Value("${image.upload.dir}")
    private String imageUploadDir;
    @Value("${logo.upload.dir}")
    private String logoUploadDir;
    private final PhotoRepository photoRepository;
    private final ConventionRepository conventionRepository;

    public PhotoService(PhotoRepository photoRepository, ConventionRepository conventionRepository) {
        this.photoRepository = photoRepository;
        this.conventionRepository = conventionRepository;
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public Photo storePhoto(MultipartFile file, Convention convention) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(imageUploadDir, filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Photo photo = new Photo();
        photo.setFileName(filename);
        photo.setConvention(convention);
        return photoRepository.save(photo);
    }

    public Resource loadPhoto(Long id) throws MalformedURLException {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        if (photoOptional.isEmpty()) {
            throw new RuntimeException("Could not read file");
        }

        Photo photo = photoOptional.get();
        Path filePath = Paths.get(imageUploadDir, photo.getFileName());
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read file: " + photo.getFileName());
        }
    }

    public String storeLogo(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(logoUploadDir, filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    public Resource loadLogo(Long id) throws MalformedURLException {
        Optional<Convention> conventionOptional = conventionRepository.findById(id);
        if (conventionOptional.isEmpty()) {
            throw new RuntimeException("Could not read file");
        }

        Convention convention = conventionOptional.get();
        Path filePath = Paths.get(logoUploadDir, convention.getLogo());
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read file: " + convention.getLogo());
        }
    }

    public void deleteLogoFile(String filename) throws IOException {
        Path filePath = Paths.get(logoUploadDir).resolve(filename);
        Files.deleteIfExists(filePath);
    }

    public void deletePhotoFile(String filename) throws IOException {
        Path filePath = Paths.get(imageUploadDir).resolve(filename);
        Files.deleteIfExists(filePath);
    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public List<Photo> getPhotosByConventionId(Long id) {
        return photoRepository.findAllByConventionId(id);
    }
}
