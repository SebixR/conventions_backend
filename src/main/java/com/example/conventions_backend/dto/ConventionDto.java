package com.example.conventions_backend.dto;

import com.example.conventions_backend.entities.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
public class ConventionDto {
    private Long id;
    private Long userId;
    private String eventName;
    private String logo;
    private String selectedStartDate;
    private String selectedEndDate;
    private String city;
    private String country;
    private String address1;
    private String address2;
    private List<TicketPrice> tickets;
    private List<Link> links;
    private String description;
    private List<String> selectedTags;
    private List<Photo> photos;
    @Enumerated(EnumType.STRING)
    private ConventionStatus conventionStatus;

    public static ConventionDto fromConvention(Convention convention) {
        ConventionDto conventionDto = new ConventionDto();
        conventionDto.setUserId(convention.getUser().getId());
        conventionDto.setEventName(convention.getName());
        conventionDto.setLogo(convention.getLogo());
        conventionDto.setSelectedStartDate(convention.getStartDate().toString());
        conventionDto.setSelectedEndDate(convention.getEndDate().toString());
        conventionDto.setCity(convention.getAddress().getCity());
        conventionDto.setCountry(convention.getAddress().getCountry());
        conventionDto.setAddress1(convention.getAddress().getAddress1());
        conventionDto.setAddress2(convention.getAddress().getAddress2());
        //conventionDto.setTickets(convention.getTicketPrices());
        //conventionDto.setLinks(convention.getLinks());
        conventionDto.setDescription(convention.getDescription());
        List<String> tags = new ArrayList<>();
        for (Tag tag : convention.getTags()) {
            tags.add(tag.getTag());
        }
        conventionDto.setSelectedTags(tags);
        //conventionDto.setPhotos(convention.getPhotos());
        conventionDto.setConventionStatus(convention.getConventionStatus());

        return conventionDto;
    }
}
