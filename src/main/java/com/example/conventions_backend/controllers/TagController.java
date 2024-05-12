package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.TagDto;
import com.example.conventions_backend.entities.Tag;
import com.example.conventions_backend.services.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/public")
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getAllTags")
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag : tags) {
            tagDtos.add(new TagDto().fromTag(tag));
        }
        return tagDtos;
    }
}
