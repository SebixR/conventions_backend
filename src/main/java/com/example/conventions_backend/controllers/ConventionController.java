package com.example.conventions_backend.controllers;

import com.example.conventions_backend.dto.ConventionDto;
import com.example.conventions_backend.dto.FilterRequestDto;
import com.example.conventions_backend.entities.*;
import com.example.conventions_backend.services.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("public/getConvention/{id}")
    public ResponseEntity<ConventionDto> getConventionById(@PathVariable("id") Long id) {
        Optional<Convention> conventionOptional = conventionService.getConvention(id);

        if (conventionOptional.isPresent()) {
            ConventionDto conventionDto = ConventionDto.fromConvention(conventionOptional.get());
            conventionDto.setId(conventionOptional.get().getId());

            conventionDto.setLinks(linkService.getLinksByConventionId(id));
            conventionDto.setPhotos(photoService.getPhotosByConventionId(id));
            conventionDto.setTickets(ticketPriceService.getTicketPricesByConventionId(id));

            return ResponseEntity.ok(conventionDto);
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

    @GetMapping("auth/getConventionsByUser/{id}")
    public ResponseEntity<List<ConventionDto>> getConventionsByUser(@PathVariable("id") Long id) {
        List<Convention> conventions = conventionService.getConventionsByUser(id);

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

    @PostMapping("public/filterConventions")
    public ResponseEntity<List<ConventionDto>> getFilteredConventions(@RequestBody FilterRequestDto filterRequestDto) {
        List<Convention> conventions = conventionService.getFilteredConventions(filterRequestDto);

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

    @PostMapping("auth/deleteConvention")
    public void deleteConvention(@RequestParam("id") Long id, @RequestParam("photos") List<String> photosToSkip) {
        Optional<Convention> conventionOptional = conventionService.getConvention(id);
        if (conventionOptional.isEmpty()) return;

        if (!photosToSkip.contains(conventionOptional.get().getLogo())) {
            try {
                photoService.deleteLogoFile(conventionOptional.get().getLogo());
            } catch (IOException e) {
                System.out.println("Failed to delete the logo file");
            }
        }

        List<Photo> photos = photoService.getPhotosByConventionId(id);
        for(Photo photo : photos) {
            if (!photosToSkip.contains(photo.getFileName())) {
                try {
                    photoService.deletePhotoFile(photo.getFileName());
                } catch (IOException e) {
                    System.out.println("Failed to delete photo: " + photo.getFileName());
                }
            }
            photoService.deletePhoto(photo.getId());
        }

        List<TicketPrice> ticketPrices = ticketPriceService.getTicketPricesByConventionId(id);
        for(TicketPrice ticketPrice : ticketPrices) {
            ticketPriceService.deleteTicketPrice(ticketPrice.getId());
        }

        List<Link> links = linkService.getLinksByConventionId(id);
        for(Link link : links) {
            linkService.deleteLink(link.getId());
        }

        conventionService.deleteConvention(id);
    }

    @PostMapping("auth/blockConvention/{id}")
    public ResponseEntity<ConventionDto> blockConvention(@PathVariable("id") Long id) {
        try {
            Convention convention = conventionService.blockConvention(id);
            ConventionDto conventionDto = ConventionDto.fromConvention(convention);
            return ResponseEntity.ok(conventionDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("auth/unblockConvention/{id}")
    public ResponseEntity<ConventionDto> unblockConvention(@PathVariable("id") Long id) {
        try {
            Optional<Convention> conventionOptional = conventionService.getConvention(id);
            if (conventionOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            ConventionStatus status = chooseStatus(conventionOptional.get().getStartDate(), conventionOptional.get().getEndDate());

            Convention convention = conventionService.unblockConvention(id, status);
            ConventionDto conventionDto = ConventionDto.fromConvention(convention);
            return ResponseEntity.ok(conventionDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

        if (appUser.getRole() == UserRole.BLOCKED)
            convention.setConventionStatus(ConventionStatus.BLOCKED);

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

        for (String filename : conventionDto.getFetchedPhotoNames()){
            Photo photo = new Photo();
            photo.setFileName(filename);
            photo.setConvention(convention);
            photoService.savePhoto(photo);
        }
        // New photos handled by the PhotoController

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

        conventionDto.setConventionStatus(chooseStatus(
                LocalDate.parse(conventionDto.getSelectedStartDate()),
                LocalDate.parse(conventionDto.getSelectedEndDate())));
        //TODO Status is never updated

        convention.setConventionStatus(conventionDto.getConventionStatus());

        return convention;
    }

    public static ConventionStatus chooseStatus(LocalDate startDate, LocalDate endDate) {
        int startDateComparison = startDate.compareTo(LocalDate.now());
        int endDateComparison = endDate.compareTo(LocalDate.now());
        if ((startDateComparison == 0 || startDateComparison < 0) //start date is today or passed
                && (endDateComparison == 0 || endDateComparison > 0)) { //end date is today or upcoming
            return ConventionStatus.ONGOING;
        }
        else if (startDateComparison > 0) { //start date is upcoming
            return ConventionStatus.UPCOMING;
        }
        else {
            return ConventionStatus.OVER;
        }
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
