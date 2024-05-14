package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.Tag;
import com.example.conventions_backend.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagByTag(String tagString) {
        return tagRepository.findTagByTag(tagString)
                .orElseThrow(() -> new NoSuchElementException("Tag with tag: " + tagString + " not found"));
    }
}
