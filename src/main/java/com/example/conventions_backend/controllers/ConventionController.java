package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.ConventionDto;
import com.example.conventions_backend.entities.*;
import com.example.conventions_backend.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ConventionController {

    private final ConventionService conventionService;
    private final AppUserService appUserService;
    private final TicketPriceService ticketPriceService;
    private final LinkService linkService;
    private final PhotoService photoService;
    private final TagService tagService;

    public ConventionController(ConventionService conventionService, AppUserService appUserService,
                                TicketPriceService ticketPriceService, LinkService linkService,
                                PhotoService photoService, TagService tagService) {
        this.conventionService = conventionService;
        this.appUserService = appUserService;
        this.ticketPriceService = ticketPriceService;
        this.linkService = linkService;
        this.photoService = photoService;
        this.tagService = tagService;
    }

    @GetMapping("/getConvention/{id}") //handles GET requests
    public ResponseEntity<Convention> getConventionById(@PathVariable("id") Long id) { //@PathVariable puts the id from the url into the Long variable
        Optional<Convention> conventionOptional = conventionService.getConvention(id);
        if (conventionOptional.isPresent()) {
            return ResponseEntity.ok(conventionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("auth/addConvention")
    public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto) {

        Convention convention = fromConventionDto(conventionDto);

        //tags are null, but right now it's an issue with the front end
        Address address = addressFromConventionDto(conventionDto);
        convention.setAddress(address);
        address.setConvention(convention);

        AppUser appUser = appUserService.getAppUserById(conventionDto.getUserId());
        convention.setUser(appUser);

        List<Tag> tags = new ArrayList<>();
        for (String tagString : conventionDto.getSelectedTags()) {
            Tag tag = tagService.getTagByTag(tagString);
            tags.add(tag);
        }
        convention.setTags(tags);

        Convention savedConvention = conventionService.saveConvention(convention);

        for (TicketPrice ticket : conventionDto.getTickets()) {
            TicketPrice ticketPrice = new TicketPrice();
            ticketPrice.setPrice(ticket.getPrice());
            ticketPrice.setDescription(ticket.getDescription());
            ticketPrice.setConvention(convention);
            ticketPriceService.saveTicketPrice(ticketPrice);
        }

        for (Link link : conventionDto.getLinks()) {
            Link newLink = new Link();
            newLink.setName(link.getName());
            newLink.setAddress(link.getAddress());
            newLink.setConvention(convention);
            linkService.saveLink(newLink);
        }



        for (Photo photo : conventionDto.getPhotos()) {
            Photo newPhoto = new Photo();
            newPhoto.setFileName(photo.getFileName());
            newPhoto.setConvention(convention);
            photoService.savePhoto(newPhoto);
        }

        ConventionDto savedConventionDto = ConventionDto.fromConvention(savedConvention);

        return ResponseEntity.ok(savedConventionDto);
    }

    private Convention fromConventionDto(ConventionDto conventionDto) { //skips all the relationships
        Convention convention = new Convention();
        convention.setName(conventionDto.getEventName());
        convention.setLogo(conventionDto.getLogo());
        convention.setStartDate(LocalDate.parse(conventionDto.getSelectedStartDate()));
        convention.setEndDate(LocalDate.parse(conventionDto.getSelectedEndDate()));
        convention.setDescription(conventionDto.getDescription());
        convention.setConventionStatus(conventionDto.getConventionStatus());

        return convention;
    }

    private Address addressFromConventionDto(ConventionDto conventionDto) {
        Address address = new Address();
        address.setCity(conventionDto.getCity());
        address.setCountry(conventionDto.getCountry());
        address.setAddress1(conventionDto.getAddress1());
        address.setAddress2(conventionDto.getAddress2());

        return address;
    }
}
