package com.example.conventions_backend;

import com.example.conventions_backend.entities.Tag;
import com.example.conventions_backend.repositories.TagRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader {
    @Autowired
    private TagRepository tagRepository;

    @PostConstruct
    public void init() {
        if (tagRepository.count() == 0) {
            addTag("Comic Books");
            addTag("Fantasy");
            addTag("Video Games");
            addTag("Science Fiction");
            addTag("Anime and Manga");
            addTag("Cosplay");
            addTag("Table Top Games");
            addTag("Role Play");
        }
    }

    public void addTag(String tagContent) {
        Tag tag = new Tag();
        tag.setTag(tagContent);
        tagRepository.save(tag);
    }
}
