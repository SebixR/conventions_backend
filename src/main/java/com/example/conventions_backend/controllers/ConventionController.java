package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.ConventionDto;
import com.example.conventions_backend.entities.*;
import com.example.conventions_backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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

    @GetMapping("public/getAllConventions")
    public ResponseEntity<List<ConventionDto>> getAllConventions() {
        List<Convention> conventions = conventionService.getAllConventions();

        List<ConventionDto> conventionDtos = new ArrayList<>();
        for (Convention convention : conventions) {
            ConventionDto conventionDto = ConventionDto.fromConvention(convention);
            conventionDto.setId(convention.getId());
            conventionDtos.add(conventionDto);
        }
        if (conventions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(conventionDtos);
        }
    }

    @PostMapping("auth/addConvention")
    public ResponseEntity<ConventionDto> addConvention(@RequestBody ConventionDto conventionDto) {

        Convention convention = fromConventionDto(conventionDto);

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

    private Convention fromConventionDto(ConventionDto conventionDto) { //skips all the relationships, as well as the id field (because there is no id yet, but we need it for get requests)
        Convention convention = new Convention();
        convention.setName(conventionDto.getEventName());
        convention.setStartDate(LocalDate.parse(conventionDto.getSelectedStartDate()));
        convention.setEndDate(LocalDate.parse(conventionDto.getSelectedEndDate()));
        convention.setDescription(conventionDto.getDescription());
        convention.setLogo(conventionDto.getLogo());

        int startDateComparison = LocalDate.parse(conventionDto.getSelectedStartDate()).compareTo(LocalDate.now());
        int endDateComparison = LocalDate.parse(conventionDto.getSelectedEndDate()).compareTo(LocalDate.now());
        if ((startDateComparison == 0 || startDateComparison < 0) //start date is today or passed
                && (endDateComparison == 0 || endDateComparison > 0)) { //end date is today or upcoming
            conventionDto.setConventionStatus(ConventionStatus.ONGOING);
        }
        else if (startDateComparison > 0) { //start date is upcoming
            conventionDto.setConventionStatus(ConventionStatus.UPCOMING);
        }
        else {
            conventionDto.setConventionStatus(ConventionStatus.OVER);
        }

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
