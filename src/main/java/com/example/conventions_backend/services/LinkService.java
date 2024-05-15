package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.Link;
import com.example.conventions_backend.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    public List<Link> getLinksByConventionId(Long id) {
        return linkRepository.findAllByConventionId(id);
    }
}
