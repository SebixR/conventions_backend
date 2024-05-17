package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.Photo;
import com.example.conventions_backend.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public List<Photo> getPhotosByConventionId(Long id) {
        return photoRepository.findAllByConventionId(id);
    }
}
